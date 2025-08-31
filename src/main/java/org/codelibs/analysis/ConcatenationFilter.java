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
package org.codelibs.analysis;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeSource;

/**
 * Abstract base class for token filters that concatenate adjacent tokens.
 * Subclasses must implement isTarget() and isConcatenated() to define
 * which tokens to process and when to concatenate them.
 */
public abstract class ConcatenationFilter extends TokenFilter {

    /** The term attribute for accessing and modifying token text */
    protected final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    /** The offset attribute for managing token offsets */
    protected final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

    /** State for storing lookahead tokens */
    protected AttributeSource.State current;

    /**
     * Creates a new ConcatenationFilter.
     *
     * @param input the input token stream
     */
    protected ConcatenationFilter(final TokenStream input) {
        super(input);
    }

    /**
     * Determines if the current token should be processed for concatenation.
     *
     * @return true if the token should be considered for concatenation
     */
    protected abstract boolean isTarget();

    /**
     * Determines if the next token should be concatenated with the current token.
     *
     * @return true if concatenation should occur
     */
    protected abstract boolean isConcatenated();

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

    /**
     * Processes the current token, potentially concatenating it with following tokens.
     *
     * @return true if a token is available
     * @throws IOException if an I/O error occurs
     */
    protected boolean processToken() throws IOException {
        if (!isTarget()) {
            return true;
        }

        final State previousState = captureState();
        if (input.incrementToken()) {
            if (isConcatenated()) {
                concatenateTerms(previousState);
                return processToken();
            } else {
                current = captureState();
                restoreState(previousState);
            }
        } else {
            restoreState(previousState);
        }
        return true;
    }

    /**
     * Concatenates the current token with the previous token.
     *
     * @param previousState the state of the previous token
     */
    protected void concatenateTerms(final State previousState) {
        final String term = termAtt.toString();
        final int endOffset = offsetAtt.endOffset();

        restoreState(previousState);

        termAtt.append(term);
        offsetAtt.setOffset(offsetAtt.startOffset(), endOffset);
    }

}
