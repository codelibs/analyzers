/*
 * Copyright 2009-2015 the CodeLibs Project and the Others.
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
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.junit.Test;

public class PatternConcatenationFilterTest extends BaseTokenStreamTestCase {

    @Test
    public void testBasic() throws IOException {
        final Pattern pattern1 = Pattern.compile("平成|昭和");
        final Pattern pattern2 = Pattern.compile("[0-9]+年");
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new PatternConcatenationFilter(tokenizer, pattern1, pattern2));
            }
        };

        assertAnalyzesTo(analyzer, "平成 10年", //
                new String[] { "平成10年" }, //
                new int[] { 0 },//
                new int[] { 6 },//
                new int[] { 1 });
        assertAnalyzesTo(analyzer, "aaa 昭和 56年 bbb", //
                new String[] { "aaa", "昭和56年", "bbb" }, //
                new int[] { 1, 1, 1 });
        assertAnalyzesTo(analyzer, "大正 5年", //
                new String[] { "大正", "5年" }, //
                new int[] { 1, 1 });

    }

}
