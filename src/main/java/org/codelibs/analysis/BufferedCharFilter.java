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
    public int read(final char[] cbuf, final int off, final int len) throws IOException {
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
