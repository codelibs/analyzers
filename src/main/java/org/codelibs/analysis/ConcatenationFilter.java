package org.codelibs.analysis;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeSource;

public abstract class ConcatenationFilter extends TokenFilter {

    protected final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    protected final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

    protected AttributeSource.State current;

    protected ConcatenationFilter(final TokenStream input) {
        super(input);
    }

    protected abstract boolean isTarget();

    protected abstract boolean isConcatenated();

    @Override
    public final boolean incrementToken() throws IOException {
        if (current != null) {
            restoreState(current);
            current = null;
            return processToken();
        }

        if (!input.incrementToken()) {
            return false;
        }

        return processToken();
    }

    protected boolean processToken() throws IOException {
        if (!isTarget()) {
            return true;
        }

        final State previousState = captureState();
        if (input.incrementToken()) {
            if (isConcatenated()) {
                concatenateTerms(previousState);
            } else {
                current = captureState();
                restoreState(previousState);
            }
        } else {
            restoreState(previousState);
        }
        return true;
    }

    protected void concatenateTerms(final State previousState) {
        final String term = termAtt.toString();
        final int endOffset = offsetAtt.endOffset();

        restoreState(previousState);

        termAtt.append(term);
        offsetAtt.setOffset(offsetAtt.startOffset(), endOffset);
    }

}
