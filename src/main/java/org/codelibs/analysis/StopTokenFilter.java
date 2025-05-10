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
import java.util.Locale;

import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public abstract class StopTokenFilter extends FilteringTokenFilter {

    protected final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    protected final String[] words;

    protected final boolean ignoreCase;

    public StopTokenFilter(TokenStream in, String[] words, boolean ignoreCase) {
        super(in);
        this.words = words;
        this.ignoreCase = ignoreCase;
    }

    @Override
    protected boolean accept() throws IOException {
        String text = new String(termAtt.buffer(), 0, termAtt.length());
        if (ignoreCase) {
            text = text.toLowerCase(Locale.ROOT);
        }
        for (String word : words) {
            if (accept(text, word)) {
                return false;
            }
        }
        return true;
    }

    protected abstract boolean accept(final String text, String word);
}
