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

public class CharTypeFilterTest extends BaseTokenStreamTestCase {

    @Test
    public void testAlphabetic() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new CharTypeFilter(tokenizer, true, false, false));
            }
        };

        assertAnalyzesTo(analyzer, "aaa 111 あああ aa1 aaあ 11あ", //
                new String[] { "aaa", "aa1", "aaあ" }, //
                new int[] { 1, 3, 1 });

        String symbolStr = "!\"#$%&'()*+-.,/:;<=>?@[\\]^_`{|}~";
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = "a" + symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[] { target });
        }
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = "1" + symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[0]);
        }
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = "あ" + symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[0]);
        }
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[0]);
        }
    }

    @Test
    public void testDigit() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new CharTypeFilter(tokenizer, false, true, false));
            }
        };

        assertAnalyzesTo(analyzer, "aaa 111 あああ aa1 aaあ 11あ", //
                new String[] { "111", "aa1", "11あ" }, //
                new int[] { 2, 2, 2 });

        String symbolStr = "!\"#$%&'()*+-.,/:;<=>?@[\\]^_`{|}~";
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = "a" + symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[0]);
        }
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = "1" + symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[] { target });
        }
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = "あ" + symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[0]);
        }
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[0]);
        }
    }

    @Test
    public void testLetter() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new CharTypeFilter(tokenizer, false, false, true));
            }
        };

        assertAnalyzesTo(analyzer, "aaa 111 あああ aa1 aaあ 11あ", //
                new String[] { "aaa", "あああ", "aa1", "aaあ", "11あ" }, //
                new int[] { 1, 2, 1, 1, 1 });

        String symbolStr = "!\"#$%&'()*+-.,/:;<=>?@[\\]^_`{|}~";
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = "a" + symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[] { target });
        }
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = "1" + symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[0]);
        }
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = "あ" + symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[] { target });
        }
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[0]);
        }
    }

    @Test
    public void testNone() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new CharTypeFilter(tokenizer, false, false, false));
            }
        };

        assertAnalyzesTo(analyzer, "aaa 111 あああ aa1 aaあ 11あ", //
                new String[0]);

        String symbolStr = "!\"#$%&'()*+-.,/:;<=>?@[\\]^_`{|}~";
        for (int i = 0; i < symbolStr.length(); i++) {
            String target = symbolStr.substring(i, i + 1);
            assertAnalyzesTo(analyzer, target, new String[0]);
        }
    }

    @Test
    public void testAlphabeticUpperCase() throws IOException {
        // Test that uppercase letters are correctly filtered
        // The alphabetic filter checks for lowercase 'a'-'z' only
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new CharTypeFilter(tokenizer, true, false, false));
            }
        };

        // Uppercase should not match since the condition is 'a' <= c && c <= 'z'
        assertAnalyzesTo(analyzer, "AAA BBB CCC", new String[0]);

        // Mixed case should match only if it contains lowercase
        assertAnalyzesTo(analyzer, "AaA BbB CcC", new String[] { "AaA", "BbB", "CcC" });

        // Lowercase should match
        assertAnalyzesTo(analyzer, "abc def", new String[] { "abc", "def" });
    }

    @Test
    public void testAlphabeticBoundaryCharacters() throws IOException {
        // Test boundary characters for 'a' <= c && c <= 'z' condition
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new CharTypeFilter(tokenizer, true, false, false));
            }
        };

        // Test exact boundaries
        assertAnalyzesTo(analyzer, "a", new String[] { "a" }); // 'a' (U+0061)
        assertAnalyzesTo(analyzer, "z", new String[] { "z" }); // 'z' (U+007A)

        // Test just outside boundaries
        assertAnalyzesTo(analyzer, "`", new String[0]); // '`' (U+0060, just before 'a')
        assertAnalyzesTo(analyzer, "{", new String[0]); // '{' (U+007B, just after 'z')

        // Test characters adjacent to boundaries
        assertAnalyzesTo(analyzer, "@a", new String[] { "@a" }); // Contains 'a'
        assertAnalyzesTo(analyzer, "z{", new String[] { "z{" }); // Contains 'z'
    }

    @Test
    public void testDigitBoundaryCharacters() throws IOException {
        // Test digit detection with various Unicode digits
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new CharTypeFilter(tokenizer, false, true, false));
            }
        };

        // ASCII digits
        assertAnalyzesTo(analyzer, "0123456789", new String[] { "0123456789" });

        // Full-width digits
        assertAnalyzesTo(analyzer, "０１２３４５６７８９", new String[] { "０１２３４５６７８９" });
    }
}
