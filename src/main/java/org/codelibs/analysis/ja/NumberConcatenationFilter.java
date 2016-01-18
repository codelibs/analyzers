package org.codelibs.analysis.ja;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.CharArraySet;
import org.codelibs.analysis.ConcatenationFilter;

public class NumberConcatenationFilter extends ConcatenationFilter {
    protected CharArraySet words;

    public NumberConcatenationFilter(TokenStream input, CharArraySet words) {
        super(input);
        this.words = words;
    }

    @Override
    protected boolean isTarget() {
        for (int i = 0; i < termAtt.length(); i++) {
            char c = termAtt.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean isConcatenated() {
        return words.contains(termAtt.buffer(), 0, termAtt.length());
    }
}
