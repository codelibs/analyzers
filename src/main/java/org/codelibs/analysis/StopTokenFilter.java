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
package org.codelibs.analysis;

import java.io.IOException;
import java.util.Locale;

import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Abstract base class for stop token filters that match tokens against a word list.
 * This filter extends Lucene's FilteringTokenFilter and provides a framework for implementing
 * custom stop word matching logic based on an array of stop words.
 *
 * <p>Subclasses must implement the {@code accept(String, String)} method to define
 * the specific matching criteria (e.g., prefix matching, suffix matching, exact matching).</p>
 *
 * <p>The filter supports both case-sensitive and case-insensitive matching. When case-insensitive
 * matching is enabled, all comparisons are performed using lowercase text with the ROOT locale.</p>
 */
public abstract class StopTokenFilter extends FilteringTokenFilter {

    /** Character term attribute for accessing the current token's text */
    protected final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    /** Array of stop words to match against */
    protected final String[] words;

    /** Whether to ignore case when matching stop words */
    protected final boolean ignoreCase;

    /**
     * Constructs a StopTokenFilter with the specified input stream, stop words, and case sensitivity.
     *
     * @param in the input TokenStream to filter
     * @param words the array of stop words to match against
     * @param ignoreCase whether to ignore case when matching stop words
     */
    public StopTokenFilter(TokenStream in, String[] words, boolean ignoreCase) {
        super(in);
        this.words = words;
        this.ignoreCase = ignoreCase;
    }

    @Override
    protected boolean accept() throws IOException {
        String text = new String(termAtt.buffer(), 0, termAtt.length());
        if (ignoreCase) {
            text = text.toLowerCase(Locale.ROOT);
        }
        for (String word : words) {
            if (accept(text, word)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines whether the given text should be accepted based on a comparison with a stop word.
     * This method defines the specific matching criteria for the filter.
     *
     * @param text the current token's text (possibly converted to lowercase if ignoreCase is true)
     * @param word the stop word to compare against
     * @return true if the token should be filtered out (rejected), false if it should pass through
     */
    protected abstract boolean accept(final String text, String word);
}
