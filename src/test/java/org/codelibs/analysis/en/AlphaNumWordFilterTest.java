/*
 * Copyright 2009-2016 the CodeLibs Project and the Others.
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

import static org.apache.lucene.analysis.standard.StandardTokenizer.HANGUL;
import static org.apache.lucene.analysis.standard.StandardTokenizer.HIRAGANA;
import static org.apache.lucene.analysis.standard.StandardTokenizer.IDEOGRAPHIC;
import static org.apache.lucene.analysis.standard.StandardTokenizer.KATAKANA;
import static org.apache.lucene.analysis.standard.StandardTokenizer.TOKEN_TYPES;
import static org.apache.lucene.analysis.tokenattributes.TypeAttribute.DEFAULT_TYPE;
import static org.codelibs.analysis.en.AlphaNumWordFilter.ALPHANUM;
import static org.codelibs.analysis.en.AlphaNumWordFilter.NUM;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.junit.Test;

public class AlphaNumWordFilterTest extends BaseTokenStreamTestCase {

    @Test
    public void testBasic() throws Exception {
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new NGramTokenizer(1, 1);
                return new TokenStreamComponents(tokenizer, new AlphaNumWordFilter(tokenizer));
            }
        };
        String input;

        input = "レッド";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "レ", "ッ", "ド" }, // output
                new int[] { 0, 1, 2 }, // startOffsets
                new int[] { 1, 2, 3 }, //endOffsets
                new String[] { TOKEN_TYPES[KATAKANA], TOKEN_TYPES[KATAKANA], TOKEN_TYPES[KATAKANA] }, //types
                new int[] { 1, 1, 1 } // posIncrements
        );

        input = "楽しい";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "楽", "し", "い" }, // output
                new int[] { 0, 1, 2 }, // startOffsets
                new int[] { 1, 2, 3 }, //endOffsets
                new String[] { TOKEN_TYPES[IDEOGRAPHIC], TOKEN_TYPES[HIRAGANA], TOKEN_TYPES[HIRAGANA] }, //types
                new int[] { 1, 1, 1 } // posIncrements
        );

        input = "aaa亜bbb";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "aaa", "亜", "bbb" }, // output
                new int[] { 0, 3, 4 }, // startOffsets
                new int[] { 3, 4, 7 }, //endOffsets
                new String[] { TOKEN_TYPES[ALPHANUM], TOKEN_TYPES[IDEOGRAPHIC], TOKEN_TYPES[ALPHANUM] }, //types
                new int[] { 1, 1, 1 } // posIncrements
        );

        input = "aaa bbb";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "aaa", " ", "bbb" }, // output
                new int[] { 0, 3, 4 }, // startOffsets
                new int[] { 3, 4, 7 }, //endOffsets
                new String[] { TOKEN_TYPES[ALPHANUM], DEFAULT_TYPE, TOKEN_TYPES[ALPHANUM] }, //types
                new int[] { 1, 1, 1 } // posIncrements
        );

        input = "あ1";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "あ", "1" }, // output
                new int[] { 0, 1 }, // startOffsets
                new int[] { 1, 2 }, //endOffsets
                new String[] { TOKEN_TYPES[HIRAGANA], TOKEN_TYPES[NUM] }, //types
                new int[] { 1, 1 } // posIncrements
        );

        input = "aa1";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "aa1" }, // output
                new int[] { 0 }, // startOffsets
                new int[] { 3 }, //endOffsets
                new String[] { TOKEN_TYPES[ALPHANUM] }, //types
                new int[] { 1 } // posIncrements
        );

        input = "aaa";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "aaa" }, // output
                new int[] { 0 }, // startOffsets
                new int[] { 3 }, //endOffsets
                new String[] { TOKEN_TYPES[ALPHANUM] }, //types
                new int[] { 1 } // posIncrements
        );

        input = "1あ";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "1", "あ" }, // output
                new int[] { 0, 1 }, // startOffsets
                new int[] { 1, 2 }, //endOffsets
                new String[] { TOKEN_TYPES[NUM], TOKEN_TYPES[HIRAGANA] }, //types
                new int[] { 1, 1 } // posIncrements
        );

        input = "亜";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "亜" }, // output
                new int[] { 0 }, // startOffsets
                new int[] { 1 }, //endOffsets
                new String[] { TOKEN_TYPES[IDEOGRAPHIC] }, //types
                new int[] { 1 } // posIncrements
        );

        input = "ㅏ";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "ㅏ" }, // output
                new int[] { 0 }, // startOffsets
                new int[] { 1 }, //endOffsets
                new String[] { TOKEN_TYPES[HANGUL] }, //types
                new int[] { 1 } // posIncrements
        );

        input = "ア";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "ア" }, // output
                new int[] { 0 }, // startOffsets
                new int[] { 1 }, //endOffsets
                new String[] { TOKEN_TYPES[KATAKANA] }, //types
                new int[] { 1 } // posIncrements
        );

        input = "あ";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "あ" }, // output
                new int[] { 0 }, // startOffsets
                new int[] { 1 }, //endOffsets
                new String[] { TOKEN_TYPES[HIRAGANA] }, //types
                new int[] { 1 } // posIncrements
        );

        input = "a";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "a" }, // output
                new int[] { 0 }, // startOffsets
                new int[] { 1 }, //endOffsets
                new String[] { TOKEN_TYPES[ALPHANUM] }, //types
                new int[] { 1 } // posIncrements
        );

        input = "111";
        assertAnalyzesTo(analyzer, input, //
                new String[] { "111" }, // output
                new int[] { 0 }, // startOffsets
                new int[] { 3 }, //endOffsets
                new String[] { TOKEN_TYPES[NUM] }, //types
                new int[] { 1 } // posIncrements
        );

    }

}
