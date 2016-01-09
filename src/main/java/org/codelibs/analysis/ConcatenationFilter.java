/*
 * Copyright 2009-2016 the CodeLibs Project and the Others.
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

public abstract class ConcatenationFilter extends TokenFilter {

    protected final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    protected final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

    protected AttributeSource.State current;

    protected ConcatenationFilter(final TokenStream input) {
        super(input);
    }

    protected abstract boolean isTarget();

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

    protected void concatenateTerms(final State previousState) {
        final String term = termAtt.toString();
        final int endOffset = offsetAtt.endOffset();

        restoreState(previousState);

        termAtt.append(term);
        offsetAtt.setOffset(offsetAtt.startOffset(), endOffset);
    }

}
