/*
 * Copyright 2009-2014 the CodeLibs Project and the Others.
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
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.MockTokenizer;
import org.apache.lucene.analysis.TokenStream;
import org.junit.Test;

public class ProlongedSoundMarkCharFilterTest extends BaseTokenStreamTestCase {

    private TokenStream createTokeStream(final String text, String replacement) throws IOException {
        Reader cs = new ProlongedSoundMarkCharFilter(new StringReader(text),
                replacement.charAt(0));
        MockTokenizer tokenizer = new MockTokenizer(MockTokenizer.WHITESPACE, false);
        tokenizer.setReader(cs);
        return tokenizer;
    }

    @Test
    public void testBasics() throws IOException {
        String[] psms = new String[] { "\u002d", "\uff0d", "\u2010", "\u2011",
                "\u2012", "\u2013", "\u2014", "\u2015", "\u207b", "\u208b",
                "\u30fc" };
        for (String replacement : new String[] { "\u30fc", "\u208b" }) {
            for (String psm : psms) {
                assertTokenStreamContents(
                        createTokeStream("あ" + psm, replacement),
                        new String[] { "あ" + replacement }, new int[] { 0 },
                        new int[] { 2 });
                assertTokenStreamContents(
                        createTokeStream("ア" + psm, replacement),
                        new String[] { "ア" + replacement }, new int[] { 0 },
                        new int[] { 2 });

                assertTokenStreamContents(
                        createTokeStream("亜" + psm, replacement),
                        new String[] { "亜" + psm }, new int[] { 0 },
                        new int[] { 2 });
                assertTokenStreamContents(createTokeStream(psm, replacement),
                        new String[] { psm }, new int[] { 0 }, new int[] { 1 });
            }
        }
    }

}
