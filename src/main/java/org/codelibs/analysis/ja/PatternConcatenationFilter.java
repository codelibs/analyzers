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

import java.util.regex.Pattern;

import org.apache.lucene.analysis.TokenStream;
import org.codelibs.analysis.ConcatenationFilter;

/**
 * A token filter that uses regular expression patterns to determine token concatenation behavior.
 * This filter extends the base ConcatenationFilter and uses two patterns: one to identify
 * target tokens for potential concatenation, and another to determine whether concatenation
 * should actually occur.
 *
 * <p>This filter is useful for Japanese text analysis where specific character patterns
 * need to be handled with custom concatenation logic based on regular expression matching.</p>
 */
public class PatternConcatenationFilter extends ConcatenationFilter {

    /** Regular expression pattern used to identify target tokens */
    private Pattern pattern1;

    /** Regular expression pattern used to determine concatenation behavior */
    private Pattern pattern2;

    /**
     * Constructs a PatternConcatenationFilter with the specified input token stream and patterns.
     *
     * @param input the input TokenStream to filter
     * @param pattern1 the Pattern used to identify target tokens for potential concatenation
     * @param pattern2 the Pattern used to determine whether concatenation should occur
     */
    public PatternConcatenationFilter(TokenStream input, Pattern pattern1, Pattern pattern2) {
        super(input);
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;
    }

    @Override
    protected boolean isTarget() {
        return pattern1.matcher(termAtt).matches();
    }

    @Override
    protected boolean isConcatenated() {
        return pattern2.matcher(termAtt).matches();
    }

}
