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
import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * A stop word filter that can dynamically reload its stop word set from a file.
 * This filter extends Lucene's FilteringTokenFilter and adds the capability to automatically
 * reload the stop word list from a specified file path when the file is modified.
 *
 * <p>The filter monitors the stop word file for changes and reloads the stop word set based on
 * a configurable reload interval. This allows for dynamic updates to the stop word list without
 * requiring analyzer or application restart.</p>
 *
 * <p>Stop words are loaded from a text file with one stop word per line, using UTF-8 encoding.
 * The filter can be configured to perform case-sensitive or case-insensitive matching.</p>
 */
public class ReloadableStopFilter extends FilteringTokenFilter {

    /** Initial capacity for the stop word set */
    private static final int INITIAL_CAPACITY = 16;

    /** Character term attribute for accessing the current token's text */
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    /** Set containing the current stop words loaded from file */
    private CharArraySet stopWords;

    /** Path to the file containing stop words */
    private final Path stopWordPath;

    /** Interval in milliseconds between reload checks */
    private long reloadInterval;

    /** Timestamp when the next reload check should occur */
    private long expiry;

    /** Whether to ignore case when matching stop words */
    private boolean ignoreCase;

    /** Last modification time of the stop word file when it was loaded */
    private long lastModified;

    /**
     * Constructs a ReloadableStopFilter with the specified input stream, stop word file path, case sensitivity, and reload interval.
     *
     * @param in the input TokenStream to filter
     * @param stopwordPath the Path to the file containing stop words (one per line, UTF-8 encoded)
     * @param ignoreCase whether to ignore case when matching stop words
     * @param reloadInterval the interval in milliseconds between checks for file modifications
     */
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
            if (Files.getLastModifiedTime(stopWordPath).toMillis() > lastModified) {
                loadStopWordSet();
            }
            expiry = System.currentTimeMillis() + reloadInterval;
        }
        super.reset();
    }

    private void loadStopWordSet() {
        try (BufferedReader reader = Files.newBufferedReader(stopWordPath, StandardCharsets.UTF_8)) {
            stopWords = WordlistLoader.getWordSet(reader, new CharArraySet(INITIAL_CAPACITY, ignoreCase));
            lastModified = Files.getLastModifiedTime(stopWordPath).toMillis();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read stop word file: " + stopWordPath, e);
        }
    }

}
