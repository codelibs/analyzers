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
package org.codelibs.analysis.ja;

import java.util.Set;

import org.apache.lucene.analysis.TokenStream;
import org.codelibs.analysis.ConcatenationFilter;

public class PosConcatenationFilter extends ConcatenationFilter {

    private final Set<String> posTags;

    private final PartOfSpeechSupplier supplier;

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

    public interface PartOfSpeechSupplier {
        String get();
    }
}
