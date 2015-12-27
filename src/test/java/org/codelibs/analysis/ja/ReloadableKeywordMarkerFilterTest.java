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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.junit.Test;

public class ReloadableKeywordMarkerFilterTest extends BaseTokenStreamTestCase {

    @Test
    public void testBasic() throws Exception {
        final Path dictPath = Files.createTempFile("rkmf_", ".txt");
        final long reloadInterval = 1000;
        writeFile(dictPath, "aaa");

        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(final String fieldName, final Reader reader) {
                final Tokenizer tokenizer = new WhitespaceTokenizer(reader);
                return new TokenStreamComponents(tokenizer, new ReloadableKeywordMarkerFilter(tokenizer, dictPath, reloadInterval));
            }
        };

        String input = "aaa bbb";
        assertTokenStreamContents(analyzer.tokenStream("dummy", input), new String[] { "aaa", "bbb" }, new int[] { 0, 4 },
                new int[] { 3, 7 }, null, null, null, input.length(), new boolean[] { true, false }, true);

        writeFile(dictPath, "bbb");
        Thread.sleep(1100L);

        assertTokenStreamContents(analyzer.tokenStream("dummy", input), new String[] { "aaa", "bbb" }, new int[] { 0, 4 },
                new int[] { 3, 7 }, null, null, null, input.length(), new boolean[] { false, true }, true);

    }

    private void writeFile(Path dictPath, String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(dictPath, Charset.forName("UTF-8"))) {
            writer.write(content);
        }
    }

}
