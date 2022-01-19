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

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.junit.Test;

public class StopTokenSuffixFilterTest extends BaseTokenStreamTestCase {

    @Test
    public void testBasic() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new StopTokenSuffixFilter(tokenizer, new String[] { "b", "dd" }, false));
            }
        };

        assertAnalyzesTo(analyzer, "aaa bbb ccc ddd eee", //
                new String[] { "aaa", "ccc", "eee" }, //
                new int[] { 0, 8, 16 }, //
                new int[] { 3, 11, 19 }, //
                new int[] { 1, 2, 2 });
        assertAnalyzesTo(analyzer, "aaa", new String[] { "aaa" });
        assertAnalyzesTo(analyzer, "ddd", new String[0]);
        assertAnalyzesTo(analyzer, "add", new String[0]);
        assertAnalyzesTo(analyzer, "aad", new String[] { "aad" });
        assertAnalyzesTo(analyzer, "dda", new String[] { "dda" });
        assertAnalyzesTo(analyzer, "daa", new String[] { "daa" });

    }

}
