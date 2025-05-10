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

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class ReloadableKeywordMarkerFilter extends KeywordMarkerFilter {

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    private CharArraySet keywordSet;

    private final Path keywordPath;

    private long reloadInterval;

    private long expiry;

    private long lastModifed;

    public ReloadableKeywordMarkerFilter(TokenStream in, Path keywordPath, long reloadInterval) {
        super(in);
        this.keywordPath = keywordPath;
        this.reloadInterval = reloadInterval;

        loadKeywordSet();
        expiry = System.currentTimeMillis() + reloadInterval;
    }

    @Override
    protected boolean isKeyword() {
        return keywordSet.contains(termAtt.buffer(), 0, termAtt.length());
    }

    @Override
    public void reset() throws IOException {
        if (expiry < System.currentTimeMillis()) {
            if (Files.getLastModifiedTime(keywordPath).toMillis() > lastModifed) {
                loadKeywordSet();
            }
            expiry = System.currentTimeMillis() + reloadInterval;
        }
        super.reset();
    }

    private void loadKeywordSet() {
        try (BufferedReader reader = Files.newBufferedReader(keywordPath, Charset.forName("UTF-8"))) {
            keywordSet = WordlistLoader.getWordSet(reader);
            lastModifed = Files.getLastModifiedTime(keywordPath).toMillis();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read " + keywordPath, e);
        }
    }

}
