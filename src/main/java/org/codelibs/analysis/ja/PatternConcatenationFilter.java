package org.codelibs.analysis.ja;

import java.util.regex.Pattern;

import org.apache.lucene.analysis.TokenStream;
import org.codelibs.analysis.ConcatenationFilter;

public class PatternConcatenationFilter extends ConcatenationFilter {

    private Pattern pattern1;

    private Pattern pattern2;

    public PatternConcatenationFilter(TokenStream input, Pattern pattern1, Pattern pattern2) {
        super(input);
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;
    }

    @Override
    protected boolean isTarget() {
        return pattern1.matcher(termAtt).matches();
    }

    @Override
    protected boolean isConcatenated() {
        return pattern2.matcher(termAtt).matches();
    }

}
