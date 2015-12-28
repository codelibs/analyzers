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

package org.codelibs.analysis.en;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.codelibs.analysis.en.ReloadableKeywordMarkerFilter;
import org.junit.Test;

public class ReloadableKeywordMarkerFilterTest extends BaseTokenStreamTestCase {

    @Test
    public void testBasic() throws Exception {
        final Path dictPath = Files.createTempFile("rkmf_", ".txt");
        final long reloadInterval = 500;
        writeFile(dictPath, "aaa");

        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName) {
                final Tokenizer tokenizer = new WhitespaceTokenizer();
                return new TokenStreamComponents(tokenizer, new ReloadableKeywordMarkerFilter(tokenizer, dictPath, reloadInterval));
            }
        };

        String input = "aaa bbb";
        assertTokenStreamContents(analyzer.tokenStream("dummy", input), new String[] { "aaa", "bbb" }, new int[] { 0, 4 },
                new int[] { 3, 7 }, null, null, null, input.length(), new boolean[] { true, false }, true);

        Thread.sleep(1000L);
        writeFile(dictPath, "bbb");
        Thread.sleep(1000L);

        assertTokenStreamContents(analyzer.tokenStream("dummy", input), new String[] { "aaa", "bbb" }, new int[] { 0, 4 },
                new int[] { 3, 7 }, null, null, null, input.length(), new boolean[] { false, true }, true);

    }

    private void writeFile(Path dictPath, String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(dictPath, Charset.forName("UTF-8"))) {
            writer.write(content);
        }
    }

}
