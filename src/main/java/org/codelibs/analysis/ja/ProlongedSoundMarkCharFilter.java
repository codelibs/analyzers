/*
 * Copyright 2012-2022 CodeLibs Project and the Others.
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

import java.io.Reader;
import java.lang.Character.UnicodeBlock;

import org.codelibs.analysis.BufferedCharFilter;

public class ProlongedSoundMarkCharFilter extends BufferedCharFilter {

    private static final char U002D = '\u002d'; // HYPHEN-MINUS

    private static final char UFF0D = '\uff0d'; // FULLWIDTH HYPHEN-MINUS

    private static final char U2010 = '\u2010'; // HYPHEN

    private static final char U2011 = '\u2011'; // NON-BREAKING HYPHEN

    private static final char U2012 = '\u2012'; // FIGURE DASH

    private static final char U2013 = '\u2013'; // EN DASH

    private static final char U2014 = '\u2014'; // EM DASH

    private static final char U2015 = '\u2015'; // HORIZONTAL BAR

    private static final char U207B = '\u207b'; // SUPERSCRIPT MINUS

    private static final char U208B = '\u208b'; // SUBSCRIPT MINUS

    private static final char U30FC = '\u30fc'; // KATAKANA-HIRAGANA SOUND MARK

    private final char replacement;

    public ProlongedSoundMarkCharFilter(final Reader in) {
        this(in, U30FC);
    }

    public ProlongedSoundMarkCharFilter(final Reader in, final char replacement) {
        super(in);
        this.replacement = replacement;
    }

    @Override
    protected CharSequence processInput(final CharSequence input) {
        final StringBuilder buf = new StringBuilder(input.length());
        char prev = 0;
        for (int pos = 0; pos < input.length(); pos++) {
            final char c = input.charAt(pos);
            switch (c) {
            case U002D:
            case UFF0D:
            case U2010:
            case U2011:
            case U2012:
            case U2013:
            case U2014:
            case U2015:
            case U207B:
            case U208B:
            case U30FC:
                if (prev != 0) {
                    final UnicodeBlock block = UnicodeBlock.of(prev);
                    if (block == UnicodeBlock.HIRAGANA || block == UnicodeBlock.KATAKANA
                            || block == UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS) {
                        buf.append(replacement);
                    } else {
                        buf.append(c);
                    }
                } else {
                    buf.append(c);
                }
                break;
            default:
                buf.append(c);
                break;
            }
            prev = c;
        }
        return buf;
    }

}
