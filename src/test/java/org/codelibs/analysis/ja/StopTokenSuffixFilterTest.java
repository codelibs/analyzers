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

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.tests.analysis.BaseTokenStreamTestCase;
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

    @Test
    public void testIgnoreCase() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new StopTokenSuffixFilter(tokenizer, new String[] { "B", "DD" }, true));
            }
        };

        // Test with lowercase input
        assertAnalyzesTo(analyzer, "aaa bbb ccc ddd eee", //
                new String[] { "aaa", "ccc", "eee" }, //
                new int[] { 0, 8, 16 }, //
                new int[] { 3, 11, 19 }, //
                new int[] { 1, 2, 2 });

        // Test with uppercase input
        assertAnalyzesTo(analyzer, "AAA BBB CCC DDD EEE", //
                new String[] { "AAA", "CCC", "EEE" }, //
                new int[] { 0, 8, 16 }, //
                new int[] { 3, 11, 19 }, //
                new int[] { 1, 2, 2 });

        // Test with mixed case input
        assertAnalyzesTo(analyzer, "aaa BbB ccc DdD eee", //
                new String[] { "aaa", "ccc", "eee" }, //
                new int[] { 0, 8, 16 }, //
                new int[] { 3, 11, 19 }, //
                new int[] { 1, 2, 2 });

        // Test specific cases
        assertAnalyzesTo(analyzer, "testB", new String[0]);
        assertAnalyzesTo(analyzer, "testb", new String[0]);
        assertAnalyzesTo(analyzer, "TESTB", new String[0]);
        assertAnalyzesTo(analyzer, "abcDD", new String[0]);
        assertAnalyzesTo(analyzer, "ABCdd", new String[0]);
        assertAnalyzesTo(analyzer, "bDDa", new String[] { "bDDa" });
    }

    @Test
    public void testEmptyStopWord() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new StopTokenSuffixFilter(tokenizer, new String[] { "" }, false));
            }
        };

        // Empty stop word matches all tokens since "anything".endsWith("") is true
        assertAnalyzesTo(analyzer, "aaa bbb ccc", new String[0]);
    }

    @Test
    public void testStopWordLongerThanToken() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new StopTokenSuffixFilter(tokenizer, new String[] { "running" }, false));
            }
        };

        assertAnalyzesTo(analyzer, "run", new String[] { "run" });
        assertAnalyzesTo(analyzer, "running", new String[0]);
    }

    @Test
    public void testJapaneseSuffix() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new StopTokenSuffixFilter(tokenizer, new String[] { "的" }, false));
            }
        };

        assertAnalyzesTo(analyzer, "合理的 論理 基本的", //
                new String[] { "論理" }, //
                new int[] { 4 }, //
                new int[] { 6 }, //
                new int[] { 2 });
    }

    @Test
    public void testEmptyInput() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new StopTokenSuffixFilter(tokenizer, new String[] { "a" }, false));
            }
        };

        assertAnalyzesTo(analyzer, "", new String[0]);
    }

}
