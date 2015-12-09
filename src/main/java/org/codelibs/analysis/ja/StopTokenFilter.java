package org.codelibs.analysis.ja;

import java.io.IOException;
import java.util.Locale;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.FilteringTokenFilter;

public abstract class StopTokenFilter extends FilteringTokenFilter {

    protected final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    protected final String[] words;

    protected final boolean ignoreCase;

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

    protected abstract boolean accept(final String text, String word);
}
