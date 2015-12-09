package org.codelibs.analysis.ja;

import org.apache.lucene.analysis.TokenStream;

public class StopTokenPrefixFilter extends StopTokenFilter {

    public StopTokenPrefixFilter(TokenStream in, String[] words, boolean ignoreCase) {
        super(in, words, ignoreCase);
    }

    protected boolean accept(final String text, String word) {
        return text.startsWith(word);
    }
}
