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

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.FilteringTokenFilter;
import org.apache.lucene.analysis.util.WordlistLoader;

public class ReloadableStopFilter extends FilteringTokenFilter {

    private static final int INITIAL_CAPACITY = 16;

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    private CharArraySet stopWords;

    private final Path stopWordPath;

    private long reloadInterval;

    private long expiry;

    private boolean ignoreCase;

    private long lastModifed;

    public ReloadableStopFilter(TokenStream in, Path stopwordPath, boolean ignoreCase, long reloadInterval) {
        super(in);
        this.stopWordPath = stopwordPath;
        this.ignoreCase = ignoreCase;
        this.reloadInterval = reloadInterval;

        loadStopWordSet();
        expiry = System.currentTimeMillis() + reloadInterval;
    }

    @Override
    protected boolean accept() {
        return !stopWords.contains(termAtt.buffer(), 0, termAtt.length());
    }

    @Override
    public void reset() throws IOException {
        if (expiry < System.currentTimeMillis()) {
            if (Files.getLastModifiedTime(stopWordPath).toMillis() > lastModifed) {
                loadStopWordSet();
            }
            expiry = System.currentTimeMillis() + reloadInterval;
        }
        super.reset();
    }

    private void loadStopWordSet() {
        try (BufferedReader reader = Files.newBufferedReader(stopWordPath, Charset.forName("UTF-8"))) {
            stopWords = WordlistLoader.getWordSet(reader, new CharArraySet(INITIAL_CAPACITY, ignoreCase));
            lastModifed = Files.getLastModifiedTime(stopWordPath).toMillis();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read " + stopWordPath, e);
        }
    }

}
