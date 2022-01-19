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
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.junit.Test;

public class NumberConcatenationFilterTest extends BaseTokenStreamTestCase {

    @Test
    public void testBasic() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("円");
        list.add("にん");
        final CharArraySet words = new CharArraySet(list, false);
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new NumberConcatenationFilter(tokenizer, words));
            }
        };

        assertAnalyzesTo(analyzer, "10 にん", //
                new String[] { "10にん" }, //
                new int[] { 0 }, //
                new int[] { 5 }, //
                new int[] { 1 });

        assertAnalyzesTo(analyzer, "aaa 100 円 100 あ bbb", //
                new String[] { "aaa", "100円", "100", "あ", "bbb" }, //
                new int[] { 1, 1, 1, 1, 1 });
        assertAnalyzesTo(analyzer, "100 円 bbb", //
                new String[] { "100円", "bbb" }, //
                new int[] { 1, 1 });
        assertAnalyzesTo(analyzer, "bbb 100 円", //
                new String[] { "bbb", "100円" }, //
                new int[] { 1, 1 });
        assertAnalyzesTo(analyzer, "円 bbb", //
                new String[] { "円", "bbb" }, //
                new int[] { 1, 1 });
        assertAnalyzesTo(analyzer, "1 100 円", //
                new String[] { "1", "100円" }, //
                new int[] { 1, 1 });
        assertAnalyzesTo(analyzer, "1 1 にん 2 100 円 3", //
                new String[] { "1", "1にん", "2", "100円", "3" }, //
                new int[] { 1, 1, 1, 1, 1 });

    }

}
