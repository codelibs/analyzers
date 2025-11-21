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

import java.io.IOException;

import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Token filter that accepts tokens based on character type criteria.
 * Filters tokens based on whether they contain lowercase alphabetic characters,
 * digits, or any letter characters.
 */
public class CharTypeFilter extends FilteringTokenFilter {
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    private boolean alphabetic;

    private boolean digit;

    private boolean letter;

    /**
     * Creates a new CharTypeFilter.
     *
     * @param in the input token stream
     * @param alphabetic if true, accept tokens containing lowercase alphabetic characters (a-z)
     * @param digit if true, accept tokens containing digit characters
     * @param letter if true, accept tokens containing any letter characters
     */
    public CharTypeFilter(TokenStream in, boolean alphabetic, boolean digit, boolean letter) {
        super(in);
        this.alphabetic = alphabetic;
        this.digit = digit;
        this.letter = letter;
    }

    @Override
    protected boolean accept() throws IOException {
        for (int i = 0; i < termAtt.length(); i++) {
            char c = termAtt.charAt(i);
            if (alphabetic && ('a' <= c && c <= 'z')) {
                return true;
            }
            if (digit && Character.isDigit(c)) {
                return true;
            }
            if (letter && Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

}
