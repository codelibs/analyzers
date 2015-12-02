package org.codelibs.analysis.ja;

import java.util.Set;

import org.apache.lucene.analysis.TokenStream;

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
