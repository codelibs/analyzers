package org.codelibs.analysis.ja;

import java.io.IOException;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.FilteringTokenFilter;

public class RemoveWordFilter extends FilteringTokenFilter {
    private final CharArraySet words;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    public RemoveWordFilter(TokenStream in, CharArraySet words) {
        super(in);
        this.words = words;
    }

    @Override
    protected boolean accept() throws IOException {
        return !words.contains(termAtt.buffer(), 0, termAtt.length());
    }
}
