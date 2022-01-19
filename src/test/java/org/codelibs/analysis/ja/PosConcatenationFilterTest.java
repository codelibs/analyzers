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
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.apache.lucene.analysis.ja.tokenattributes.PartOfSpeechAttribute;
import org.junit.Test;

public class PosConcatenationFilterTest extends BaseTokenStreamTestCase {
    @Test
    public void testNoPos() throws IOException {
        final Set<String> posTags = new HashSet<>();
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new JapaneseTokenizer(null, false, JapaneseTokenizer.Mode.SEARCH);
                final PartOfSpeechAttribute posAtt = tokenizer.addAttribute(PartOfSpeechAttribute.class);
                return new TokenStreamComponents(tokenizer,
                        new PosConcatenationFilter(tokenizer, posTags, new PosConcatenationFilter.PartOfSpeechSupplier() {
                            @Override
                            public String get() {
                                return posAtt.getPartOfSpeech();
                            }
                        }));
            }
        };

        assertAnalyzesTo(analyzer, "明日は詳細設計です。", //
                new String[] { "明日", "は", "詳細", "設計", "です", "。" }, //
                new int[] { 0, 2, 3, 5, 7, 9 }, //
                new int[] { 2, 3, 5, 7, 9, 10 }, //
                new int[] { 1, 1, 1, 1, 1, 1 });

    }

    @Test
    public void testBasic() throws IOException {
        final Set<String> posTags = new HashSet<>();
        posTags.add("名詞-副詞可能");
        posTags.add("名詞-形容動詞語幹");
        posTags.add("名詞-サ変接続");
        posTags.add("名詞-一般");
        posTags.add("名詞-接尾-一般");
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new JapaneseTokenizer(null, false, JapaneseTokenizer.Mode.SEARCH);
                final PartOfSpeechAttribute posAtt = tokenizer.addAttribute(PartOfSpeechAttribute.class);
                return new TokenStreamComponents(tokenizer,
                        new PosConcatenationFilter(tokenizer, posTags, new PosConcatenationFilter.PartOfSpeechSupplier() {
                            @Override
                            public String get() {
                                return posAtt.getPartOfSpeech();
                            }
                        }));
            }
        };

        assertAnalyzesTo(analyzer, "歯科医院の歯科衛生士", //
                new String[] { "歯科医院", "の", "歯科衛生士" }, //
                new int[] { 0, 4, 5 }, //
                new int[] { 4, 5, 10 }, //
                new int[] { 1, 1, 1 });

        assertAnalyzesTo(analyzer, "明日は詳細設計です。", //
                new String[] { "明日", "は", "詳細設計", "です", "。" }, //
                new int[] { 0, 2, 3, 7, 9 }, //
                new int[] { 2, 3, 7, 9, 10 }, //
                new int[] { 1, 1, 1, 1, 1 });
    }

}
