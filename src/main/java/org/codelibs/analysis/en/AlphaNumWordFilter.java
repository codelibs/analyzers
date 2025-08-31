/*
 * Copyright 2012-2025 CodeLibs Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.codelibs.analysis.en;

import static org.apache.lucene.analysis.standard.StandardTokenizer.HANGUL;
import static org.apache.lucene.analysis.standard.StandardTokenizer.HIRAGANA;
import static org.apache.lucene.analysis.standard.StandardTokenizer.IDEOGRAPHIC;
import static org.apache.lucene.analysis.standard.StandardTokenizer.KATAKANA;
import static org.apache.lucene.analysis.standard.StandardTokenizer.SOUTHEAST_ASIAN;
import static org.apache.lucene.analysis.standard.StandardTokenizer.TOKEN_TYPES;
import static org.apache.lucene.analysis.tokenattributes.TypeAttribute.DEFAULT_TYPE;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeSource;

/**
 * Token filter that concatenates adjacent alphanumeric and numeric tokens.
 * This filter helps create compound tokens from sequences of alphabetic and numeric characters
 * that appear consecutively in the token stream.
 */
public class AlphaNumWordFilter extends TokenFilter {

    /** Maximum allowed token length limit (1MB) */
    public static final int MAX_TOKEN_LENGTH_LIMIT = 1024 * 1024;

    /** Default maximum token length (255 characters) */
    public static final int DEFAULT_MAX_TOKEN_LENGTH = 255;

    /** Token type constant for alphanumeric tokens */
    public static final int ALPHANUM = 0;

    /** Token type constant for numeric tokens */
    public static final int NUM = 1;

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);

    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

    /** Current state of the token stream for lookahead processing */
    protected AttributeSource.State current;

    /** Maximum length for concatenated tokens */
    protected int maxTokenLength = DEFAULT_MAX_TOKEN_LENGTH;

    /**
     * Creates a new AlphaNumWordFilter.
     *
     * @param input the token stream to filter
     */
    public AlphaNumWordFilter(final TokenStream input) {
        super(input);
    }

    @Override
    public final boolean incrementToken() throws IOException {
        if (current != null) {
            restoreState(current);
            current = null;
            return processToken();
        }

        if (!input.incrementToken()) {
            return false;
        }

        return processToken();
    }

    private boolean processToken() throws IOException {
        final String type = getType();
        if (DEFAULT_TYPE.equals(typeAtt.type()) && !type.equals(typeAtt.type())) {
            typeAtt.setType(type);
        }
        int endOffset = offsetAtt.endOffset();
        State previousState = captureState();
        while (input.incrementToken()) {
            final String currentType = getType();
            final int currentStartOffset = offsetAtt.startOffset();

            if (currentStartOffset != endOffset) {
                if (DEFAULT_TYPE.equals(typeAtt.type()) && !currentType.equals(typeAtt.type())) {
                    typeAtt.setType(currentType);
                }
                current = captureState();
                break;
            } else if ((TOKEN_TYPES[ALPHANUM].equals(type) || TOKEN_TYPES[NUM].equals(type))
                    && (TOKEN_TYPES[ALPHANUM].equals(currentType) || TOKEN_TYPES[NUM].equals(currentType))) {
                concatenateTerms(previousState);
                previousState = captureState();
                endOffset = offsetAtt.endOffset();
            } else {
                if (DEFAULT_TYPE.equals(typeAtt.type())) {
                    typeAtt.setType(currentType);
                }
                current = captureState();
                break;
            }
        }
        restoreState(previousState);
        return true;
    }

    private void concatenateTerms(final State previousState) {
        final String term = termAtt.toString();
        final int endOffset = offsetAtt.endOffset();
        final String type2 = getType();

        restoreState(previousState);

        if (termAtt.length() < maxTokenLength) {
            termAtt.append(term);
        }
        offsetAtt.setOffset(offsetAtt.startOffset(), endOffset);
        final String type1 = getType();
        if (TOKEN_TYPES[NUM].equals(type1) && TOKEN_TYPES[ALPHANUM].equals(type2)) {
            typeAtt.setType(TOKEN_TYPES[ALPHANUM]);
        } else if (TOKEN_TYPES[ALPHANUM].equals(type1) && TOKEN_TYPES[NUM].equals(type2)) {
            typeAtt.setType(TOKEN_TYPES[ALPHANUM]);
        } else if (!type1.equals(type2)) {
            typeAtt.setType(TOKEN_TYPES[IDEOGRAPHIC]);
        } else if (DEFAULT_TYPE.equals(type1)) {
            typeAtt.setType(type1);
        }
    }

    private String getType() {
        if (!DEFAULT_TYPE.equals(typeAtt.type())) {
            return typeAtt.type();
        }

        if (termAtt.length() != 1) {
            return DEFAULT_TYPE;
        }

        final char c = termAtt.charAt(0);
        UnicodeBlock block = Character.UnicodeBlock.of(c);
        if (UnicodeBlock.HIRAGANA.equals(block)) {
            return TOKEN_TYPES[HIRAGANA];
        } else if (UnicodeBlock.KATAKANA.equals(block) //
                || UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS.equals(block)) {
            return TOKEN_TYPES[KATAKANA];
        } else if (UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(block) //
                || UnicodeBlock.HANGUL_JAMO.equals(block) //
                || UnicodeBlock.HANGUL_JAMO_EXTENDED_A.equals(block) //
                || UnicodeBlock.HANGUL_JAMO_EXTENDED_B.equals(block) //
                || UnicodeBlock.HANGUL_SYLLABLES.equals(block)) {
            return TOKEN_TYPES[HANGUL];
        } else if (UnicodeBlock.CJK_COMPATIBILITY.equals(block) //
                || UnicodeBlock.CJK_COMPATIBILITY_FORMS.equals(block) //
                || UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS.equals(block) //
                || UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT.equals(block) //
                || UnicodeBlock.CJK_RADICALS_SUPPLEMENT.equals(block) //
                || UnicodeBlock.CJK_STROKES.equals(block) //
                || UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION.equals(block) //
                || UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(block) //
                || UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A.equals(block) //
                || UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B.equals(block) //
                || UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C.equals(block) //
                || UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D.equals(block) //
        ) {
            return TOKEN_TYPES[IDEOGRAPHIC];
        } else if (UnicodeBlock.THAI.equals(block) //
                || UnicodeBlock.LAO.equals(block) //
                || UnicodeBlock.MYANMAR.equals(block) //
                || UnicodeBlock.KHMER.equals(block) //
                || UnicodeBlock.TAI_LE.equals(block) //
                || UnicodeBlock.NEW_TAI_LUE.equals(block) //
                || UnicodeBlock.TAI_THAM.equals(block) //
                || UnicodeBlock.MYANMAR_EXTENDED_A.equals(block) //
                || UnicodeBlock.TAI_VIET.equals(block) //
        ) {
            return TOKEN_TYPES[SOUTHEAST_ASIAN];
        } else if (Character.isAlphabetic(c)) {
            return TOKEN_TYPES[ALPHANUM];
        } else if (Character.isDigit(c)) {
            return TOKEN_TYPES[NUM];
        }
        return DEFAULT_TYPE;
    }

    /**
     * Sets the maximum token length for concatenated tokens.
     *
     * @param length the maximum token length (must be between 1 and MAX_TOKEN_LENGTH_LIMIT)
     * @throws IllegalArgumentException if length is invalid
     */
    public void setMaxTokenLength(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("maxTokenLength must be greater than zero");
        } else if (length > MAX_TOKEN_LENGTH_LIMIT) {
            throw new IllegalArgumentException("maxTokenLength may not exceed " + MAX_TOKEN_LENGTH_LIMIT);
        }
        if (length != maxTokenLength) {
            maxTokenLength = length;
        }
    }

    /**
     * Gets the maximum token length for concatenated tokens.
     *
     * @return the maximum token length
     */
    public int getMaxTokenLength() {
        return maxTokenLength;
    }
}
