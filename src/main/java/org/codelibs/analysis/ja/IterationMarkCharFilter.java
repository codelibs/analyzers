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

// http://ja.wikipedia.org/wiki/%E8%B8%8A%E3%82%8A%E5%AD%97
public class IterationMarkCharFilter extends BufferedCharFilter {
    private static final String UNVOICED_CONSONANT_HIRAGANA = "かきくけこさしすせそたちつてとはひふへほはひふへほ";

    private static final String VOICED_SOUND_MARK_HIRAGANA = "がぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽ";

    private static final String UNVOICED_CONSONANT_KATAKANA = "カキクケコサシスセソタチツテトハヒフヘホハヒフヘホ";

    private static final String VOICED_SOUND_MARK_KATAKANA = "ガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポ";

    private static final char U3005 = '々';

    private static final char U309D = 'ゝ';

    private static final char U309E = 'ゞ';

    private static final char U30FD = 'ヽ';

    private static final char U30FE = 'ヾ';

    private static final char U303B = '〻';

    public IterationMarkCharFilter(final Reader in) {
        super(in);
    }

    @Override
    protected CharSequence processInput(final CharSequence input) {
        final StringBuilder buf = new StringBuilder(input.length());
        int pos = 0;
        while (pos < input.length()) {
            final char c = input.charAt(pos);
            switch (c) {
            case U3005:
                if (pos + 1 < input.length()) {
                    final char next = input.charAt(pos + 1);
                    if (next == U3005) {
                        if (pos - 2 >= 0) {
                            final char prev2 = input.charAt(pos - 2);
                            final char prev1 = input.charAt(pos - 1);
                            if (UnicodeBlock.of(prev2) == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
                                buf.append(prev2);
                                buf.append(prev1);
                            } else {
                                buf.append(prev1);
                                buf.append(prev1);
                            }
                            pos += 2;
                        } else if (pos - 1 >= 0) {
                            buf.append(input.charAt(pos - 1));
                            buf.append(input.charAt(pos - 1));
                            pos += 2;
                        } else {
                            buf.append(c);
                            pos++;
                        }
                        break;
                    }
                }
                if (pos - 1 >= 0) {
                    buf.append(input.charAt(pos - 1));
                } else {
                    buf.append(c);
                }
                pos++;
                break;
            case U309D:
                if (pos - 1 >= 0) {
                    final char prev = input.charAt(pos - 1);
                    final int index = VOICED_SOUND_MARK_HIRAGANA.indexOf(prev);
                    if (index >= 0) {
                        buf.append(UNVOICED_CONSONANT_HIRAGANA.charAt(index));
                    } else {
                        buf.append(prev);
                    }
                } else {
                    buf.append(c);
                }
                pos++;
                break;
            case U309E:
                if (pos - 1 >= 0) {
                    final char prev = input.charAt(pos - 1);
                    final int index = UNVOICED_CONSONANT_HIRAGANA.indexOf(prev);
                    if (index >= 0) {
                        buf.append(VOICED_SOUND_MARK_HIRAGANA.charAt(index));
                    } else {
                        buf.append(prev);
                    }
                } else {
                    buf.append(c);
                }
                pos++;
                break;
            case U30FD:
                if (pos - 1 >= 0) {
                    final char prev = input.charAt(pos - 1);
                    final int index = VOICED_SOUND_MARK_KATAKANA.indexOf(prev);
                    if (index >= 0) {
                        buf.append(UNVOICED_CONSONANT_KATAKANA.charAt(index));
                    } else {
                        buf.append(prev);
                    }
                } else {
                    buf.append(c);
                }
                pos++;
                break;
            case U30FE:
                if (pos - 1 >= 0) {
                    final char prev = input.charAt(pos - 1);
                    final int index = UNVOICED_CONSONANT_KATAKANA.indexOf(prev);
                    if (index >= 0) {
                        buf.append(VOICED_SOUND_MARK_KATAKANA.charAt(index));
                    } else {
                        buf.append(prev);
                    }
                } else {
                    buf.append(c);
                }
                pos++;
                break;
            case U303B:
                if (pos - 1 >= 0) {
                    buf.append(input.charAt(pos - 1));
                } else {
                    buf.append(c);
                }
                pos++;
                break;
            default:
                buf.append(c);
                pos++;
                break;
            }
        }
        return buf;
    }
}
