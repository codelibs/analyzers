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

import java.util.Set;

import org.apache.lucene.analysis.TokenStream;
import org.codelibs.analysis.ConcatenationFilter;

/**
 * A token filter that determines concatenation behavior based on part-of-speech (POS) tags.
 * This filter extends the base ConcatenationFilter and uses a set of POS tags to determine
 * whether tokens should be targets for concatenation and whether they should be concatenated.
 *
 * <p>This filter is particularly useful for Japanese text analysis where linguistic analysis
 * provides POS information that can guide token concatenation decisions. It relies on a
 * PartOfSpeechSupplier to provide the current token's POS tag.</p>
 */
public class PosConcatenationFilter extends ConcatenationFilter {

    /** Set of part-of-speech tags used to determine concatenation behavior */
    private final Set<String> posTags;

    /** Supplier that provides part-of-speech information for the current token */
    private final PartOfSpeechSupplier supplier;

    /**
     * Constructs a PosConcatenationFilter with the specified input token stream, POS tags, and supplier.
     *
     * @param input the input TokenStream to filter
     * @param posTags the Set of part-of-speech tags that determine concatenation behavior
     * @param supplier the PartOfSpeechSupplier that provides POS information for tokens
     */
    public PosConcatenationFilter(final TokenStream input, Set<String> posTags, PartOfSpeechSupplier supplier) {
        super(input);
        this.posTags = posTags;
        this.supplier = supplier;
    }

    @Override
    protected boolean isTarget() {
        final String pos = supplier.get();
        return pos != null && posTags.contains(pos);
    }

    @Override
    protected boolean isConcatenated() {
        final String pos = supplier.get();
        return pos != null && posTags.contains(pos);
    }

    /**
     * Functional interface that supplies part-of-speech (POS) tag information for the current token.
     * Implementations should provide the POS tag associated with the token currently being processed.
     */
    public interface PartOfSpeechSupplier {
        /**
         * Retrieves the part-of-speech tag for the current token.
         *
         * @return the POS tag as a String, or null if no POS information is available
         */
        String get();
    }
}
