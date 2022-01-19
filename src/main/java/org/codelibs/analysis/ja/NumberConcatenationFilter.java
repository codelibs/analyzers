/*
 * Copyright 2012-2022 CodeLibs Project and the Others.
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

public class NumberConcatenationFilter extends ConcatenationFilter {
    protected CharArraySet words;

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
