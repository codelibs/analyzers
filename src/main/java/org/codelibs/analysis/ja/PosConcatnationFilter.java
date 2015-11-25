package org.codelibs.analysis.ja;

import java.util.Set;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ja.tokenattributes.PartOfSpeechAttribute;

public class PosConcatnationFilter extends ConcatenationFilter {

	private final PartOfSpeechAttribute posAtt = addAttribute(PartOfSpeechAttribute.class);

	private Set<String> posTags;

	protected PosConcatnationFilter(final TokenStream input, Set<String> posTags) {
		super(input);
		this.posTags = posTags;
	}

	@Override
	protected boolean isTarget() {
		final String pos = posAtt.getPartOfSpeech();
		return pos != null && posTags.contains(pos);
	}

	@Override
	protected boolean isConcatenated() {
		final String pos = posAtt.getPartOfSpeech();
		return pos != null && posTags.contains(pos);
	}

}
