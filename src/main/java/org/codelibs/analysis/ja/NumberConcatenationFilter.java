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
package org.codelibs.analysis.ja;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.codelibs.analysis.ConcatenationFilter;

/**
 * A token filter that concatenates tokens containing only numeric characters (digits).
 * This filter extends the base ConcatenationFilter to specifically handle numeric tokens,
 * determining whether to concatenate them based on whether they exist in a provided word set.
 *
 * <p>This filter is particularly useful for Japanese text analysis where numeric sequences
 * might need special handling for concatenation based on context.</p>
 */
public class NumberConcatenationFilter extends ConcatenationFilter {
    /** Set of words used to determine concatenation behavior */
    protected CharArraySet words;

    /**
     * Constructs a NumberConcatenationFilter with the specified input token stream and word set.
     *
     * @param input the input TokenStream to filter
     * @param words the CharArraySet containing words that determine concatenation behavior
     */
    public NumberConcatenationFilter(TokenStream input, CharArraySet words) {
        super(input);
        this.words = words;
    }

    @Override
    protected boolean isTarget() {
        for (int i = 0; i < termAtt.length(); i++) {
            char c = termAtt.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean isConcatenated() {
        return words.contains(termAtt.buffer(), 0, termAtt.length());
    }
}
