package org.codelibs.analysis.ja;

import java.io.Reader;
import java.lang.Character.UnicodeBlock;

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

    public ProlongedSoundMarkCharFilter(final Reader in) {
        super(in);
    }

    @Override
    CharSequence processInput(final CharSequence input) {
        final StringBuilder buf = new StringBuilder(input.length());
        final int pos = 0;
        char prev = 0;
        while (pos < input.length()) {
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
                if (prev != 0) {
                    final UnicodeBlock block = UnicodeBlock.of(prev);
                    if (block == UnicodeBlock.HIRAGANA
                            || block == UnicodeBlock.KATAKANA
                            || block == UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS) {
                        buf.append(U30FC);
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
