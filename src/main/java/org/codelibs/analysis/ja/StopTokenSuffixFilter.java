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
 * A stop token filter that removes tokens ending with any of the specified suffix words.
 * This filter extends the StopTokenFilter base class and implements suffix-based matching,
 * where tokens are filtered out if they end with any word in the configured stop word list.
 *
 * <p>For example, if "ing" is in the stop word list, tokens like "running", "testing", "working",
 * and "swimming" would all be filtered out.</p>
 *
 * <p>The filter supports both case-sensitive and case-insensitive matching based on the
 * configuration provided to the constructor.</p>
 */
public class StopTokenSuffixFilter extends StopTokenFilter {

    /**
     * Constructs a StopTokenSuffixFilter with the specified input stream, suffix words, and case sensitivity.
     *
     * @param in the input TokenStream to filter
     * @param words the array of suffix words to match against token endings
     * @param ignoreCase whether to ignore case when matching suffixes
     */
    public StopTokenSuffixFilter(TokenStream in, String[] words, boolean ignoreCase) {
        super(in, words, ignoreCase);
    }

    protected boolean accept(final String text, String word) {
        return text.endsWith(word);
    }

}
