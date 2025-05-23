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
package org.codelibs.analysis.en;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

public class FlexiblePorterStemFilter extends TokenFilter {
    private final FlexiblePorterStemmer stemmer;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final KeywordAttribute keywordAttr = addAttribute(KeywordAttribute.class);

    public FlexiblePorterStemFilter(TokenStream in, boolean step1, boolean step2, boolean step3, boolean step4, boolean step5,
            boolean step6) {
        super(in);
        stemmer = new FlexiblePorterStemmer(step1, step2, step3, step4, step5, step6);
    }

    @Override
    public final boolean incrementToken() throws IOException {
        if (!input.incrementToken())
            return false;

        if ((!keywordAttr.isKeyword()) && stemmer.stem(termAtt.buffer(), 0, termAtt.length()))
            termAtt.copyBuffer(stemmer.getResultBuffer(), 0, stemmer.getResultLength());
        return true;
    }
}
