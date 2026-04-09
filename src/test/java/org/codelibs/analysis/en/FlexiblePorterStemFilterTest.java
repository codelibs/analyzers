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
package org.codelibs.analysis.en;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.miscellaneous.SetKeywordMarkerFilter;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.tests.analysis.BaseTokenStreamTestCase;
import org.junit.Test;

public class FlexiblePorterStemFilterTest extends BaseTokenStreamTestCase {

    @Test
    public void testBasicStemming() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new FlexiblePorterStemFilter(tokenizer, true, true, true, true, true, true));
            }
        };

        assertAnalyzesTo(analyzer, "running", new String[] { "run" });
        assertAnalyzesTo(analyzer, "consolation", new String[] { "consol" });
        assertAnalyzesTo(analyzer, "knitting", new String[] { "knit" });
    }

    @Test
    public void testKeywordSkip() throws IOException {
        CharArraySet keywords = new CharArraySet(2, false);
        keywords.add("running");

        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer,
                        new FlexiblePorterStemFilter(new SetKeywordMarkerFilter(tokenizer, keywords), true, true, true, true, true, true));
            }
        };

        assertAnalyzesTo(analyzer, "running knitting", new String[] { "running", "knit" });
    }

    @Test
    public void testNoChangeStem() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new FlexiblePorterStemFilter(tokenizer, true, true, true, true, true, true));
            }
        };

        assertAnalyzesTo(analyzer, "knack", new String[] { "knack" });
        assertAnalyzesTo(analyzer, "knelt", new String[] { "knelt" });
    }

    @Test
    public void testEmptyInput() throws IOException {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new FlexiblePorterStemFilter(tokenizer, true, true, true, true, true, true));
            }
        };

        assertAnalyzesTo(analyzer, "", new String[] {});
    }

    @Test
    public void testSelectiveSteps() throws IOException {
        Analyzer analyzerStep1Only = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer,
                        new FlexiblePorterStemFilter(tokenizer, true, false, false, false, false, false));
            }
        };

        assertAnalyzesTo(analyzerStep1Only, "consignment", new String[] { "consignment" });
        assertAnalyzesTo(analyzerStep1Only, "consoled", new String[] { "consol" });
    }

    @Test
    public void testMixedKeywordAndNonKeyword() throws IOException {
        CharArraySet keywords = new CharArraySet(2, false);
        keywords.add("agreed");
        keywords.add("cats");

        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer,
                        new FlexiblePorterStemFilter(new SetKeywordMarkerFilter(tokenizer, keywords), true, true, true, true, true, true));
            }
        };

        assertAnalyzesTo(analyzer, "agreed cats consolation", new String[] { "agreed", "cats", "consol" });
    }
}
