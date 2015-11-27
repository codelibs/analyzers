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

public class IterationMarkCharFilterTest extends BaseTokenStreamTestCase {

    private TokenStream createTokeStream(final String text) throws IOException {
        Reader cs = new IterationMarkCharFilter(new StringReader(text));
        MockTokenizer tokenizer = new MockTokenizer(MockTokenizer.WHITESPACE, false);
        tokenizer.setReader(cs);
        return tokenizer;
    }

    @Test
    public void testBasics() throws IOException {
        for (String s : new String[] { "々", "ゝ", "ヽ", "ゞ", "〻" }) {
            assertTokenStreamContents(createTokeStream(s), new String[] { s },
                    new int[] { 0 }, new int[] { 1 });
        }

        assertTokenStreamContents(createTokeStream("時々"),
                new String[] { "時時" }, new int[] { 0 }, new int[] { 2 });
        assertTokenStreamContents(createTokeStream("明々白々"),
                new String[] { "明明白白" }, new int[] { 0 }, new int[] { 4 });
        assertTokenStreamContents(createTokeStream("赤裸々"),
                new String[] { "赤裸裸" }, new int[] { 0 }, new int[] { 3 });
        assertTokenStreamContents(createTokeStream("代々木"),
                new String[] { "代代木" }, new int[] { 0 }, new int[] { 3 });
        assertTokenStreamContents(createTokeStream("複々々線"),
                new String[] { "複複複線" }, new int[] { 0 }, new int[] { 4 });
        assertTokenStreamContents(createTokeStream("小々々支川"),
                new String[] { "小小小支川" }, new int[] { 0 }, new int[] { 5 });

        assertTokenStreamContents(createTokeStream("部分々々"),
                new String[] { "部分部分" }, new int[] { 0 }, new int[] { 4 });
        assertTokenStreamContents(createTokeStream("後手々々"),
                new String[] { "後手後手" }, new int[] { 0 }, new int[] { 4 });

        // assertTokenStreamContents(createTokeStream("部々分々"),
        // new String[] { "部分部分" }, new int[] { 0 }, new int[] { 4 });
        // assertTokenStreamContents(createTokeStream("後々手々"),
        // new String[] { "後手後手" }, new int[] { 0 }, new int[] { 4 });

        assertTokenStreamContents(createTokeStream("こゝ"),
                new String[] { "ここ" }, new int[] { 0 }, new int[] { 2 });
        assertTokenStreamContents(createTokeStream("バナヽ"),
                new String[] { "バナナ" }, new int[] { 0 }, new int[] { 3 });

        // assertTokenStreamContents(createTokeStream("くつゝける"),
        // new String[] { "くっつける" }, new int[] { 0 }, new int[] { 5 });

        assertTokenStreamContents(createTokeStream("学問のすゝめ"),
                new String[] { "学問のすすめ" }, new int[] { 0 }, new int[] { 6 });
        assertTokenStreamContents(createTokeStream("いすゞ"),
                new String[] { "いすず" }, new int[] { 0 }, new int[] { 3 });
        assertTokenStreamContents(createTokeStream("づゝ"),
                new String[] { "づつ" }, new int[] { 0 }, new int[] { 2 });
        assertTokenStreamContents(createTokeStream("ぶゞ漬け"),
                new String[] { "ぶぶ漬け" }, new int[] { 0 }, new int[] { 4 });

        assertTokenStreamContents(createTokeStream("各〻"),
                new String[] { "各各" }, new int[] { 0 }, new int[] { 2 });
        assertTokenStreamContents(createTokeStream("屡〻"),
                new String[] { "屡屡" }, new int[] { 0 }, new int[] { 2 });

    }

}
