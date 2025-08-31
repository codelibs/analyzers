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
package org.codelibs.analysis.ja;

import org.apache.lucene.analysis.TokenStream;
import org.codelibs.analysis.StopTokenFilter;

/**
 * A stop token filter that removes tokens beginning with any of the specified prefix words.
 * This filter extends the StopTokenFilter base class and implements prefix-based matching,
 * where tokens are filtered out if they start with any word in the configured stop word list.
 *
 * <p>For example, if "test" is in the stop word list, tokens like "test", "testing", "tested",
 * and "testable" would all be filtered out.</p>
 *
 * <p>The filter supports both case-sensitive and case-insensitive matching based on the
 * configuration provided to the constructor.</p>
 */
public class StopTokenPrefixFilter extends StopTokenFilter {

    /**
     * Constructs a StopTokenPrefixFilter with the specified input stream, prefix words, and case sensitivity.
     *
     * @param in the input TokenStream to filter
     * @param words the array of prefix words to match against token beginnings
     * @param ignoreCase whether to ignore case when matching prefixes
     */
    public StopTokenPrefixFilter(TokenStream in, String[] words, boolean ignoreCase) {
        super(in, words, ignoreCase);
    }

    protected boolean accept(final String text, String word) {
        return text.startsWith(word);
    }
}
