package org.codelibs.analysis;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.charfilter.BaseCharFilter;

public abstract class BufferedCharFilter extends BaseCharFilter {
    protected Reader bufferedInput = null;

    public BufferedCharFilter(final Reader in) {
        super(in);
    }

    @Override
    public int read(final char[] cbuf, final int off, final int len)
            throws IOException {
        // Buffer all input on the first call.
        if (bufferedInput == null) {
            fill();
        }

        return bufferedInput.read(cbuf, off, len);
    }

    private void fill() throws IOException {
        final StringBuilder buffered = new StringBuilder();
        final char[] temp = new char[1024];
        for (int cnt = input.read(temp); cnt > 0; cnt = input.read(temp)) {
            buffered.append(temp, 0, cnt);
        }
        bufferedInput = new StringReader(processInput(buffered).toString());
    }

    protected abstract CharSequence processInput(CharSequence input);
}
