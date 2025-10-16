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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * A keyword marker filter that can dynamically reload its keyword set from a file.
 * This filter extends Lucene's KeywordMarkerFilter and adds the capability to automatically
 * reload the keyword list from a specified file path when the file is modified.
 *
 * <p>The filter monitors the keyword file for changes and reloads the keyword set based on
 * a configurable reload interval. This allows for dynamic updates to the keyword list without
 * requiring analyzer or application restart.</p>
 *
 * <p>Keywords are loaded from a text file with one keyword per line, using UTF-8 encoding.</p>
 */
public class ReloadableKeywordMarkerFilter extends KeywordMarkerFilter {

    /** Character term attribute for accessing the current token's text */
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    /** Set containing the current keywords loaded from file */
    private CharArraySet keywordSet;

    /** Path to the file containing keywords */
    private final Path keywordPath;

    /** Interval in milliseconds between reload checks */
    private long reloadInterval;

    /** Timestamp when the next reload check should occur */
    private long expiry;

    /** Last modification time of the keyword file when it was loaded */
    private long lastModified;

    /**
     * Constructs a ReloadableKeywordMarkerFilter with the specified input stream, keyword file path, and reload interval.
     *
     * @param in the input TokenStream to filter
     * @param keywordPath the Path to the file containing keywords (one per line, UTF-8 encoded)
     * @param reloadInterval the interval in milliseconds between checks for file modifications
     */
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
            if (Files.getLastModifiedTime(keywordPath).toMillis() > lastModified) {
                loadKeywordSet();
            }
            expiry = System.currentTimeMillis() + reloadInterval;
        }
        super.reset();
    }

    private void loadKeywordSet() {
        try (BufferedReader reader = Files.newBufferedReader(keywordPath, StandardCharsets.UTF_8)) {
            keywordSet = WordlistLoader.getWordSet(reader);
            lastModified = Files.getLastModifiedTime(keywordPath).toMillis();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read keyword file: " + keywordPath, e);
        }
    }

}
