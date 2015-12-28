package org.codelibs.analysis.en;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.WordlistLoader;

public class ReloadableKeywordMarkerFilter extends KeywordMarkerFilter {

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    private CharArraySet keywordSet;

    private final Path keywordPath;

    private long reloadInterval;

    private long expiry;

    private long lastModifed;

    public ReloadableKeywordMarkerFilter(TokenStream in, Path keywordPath, long reloadInterval) {
        super(in);
        this.keywordPath = keywordPath;
        this.reloadInterval = reloadInterval;

        loadKeywordSet();
        expiry = System.currentTimeMillis() + reloadInterval;
    }

    @Override
    protected boolean isKeyword() {
        return keywordSet.contains(termAtt.buffer(), 0, termAtt.length());
    }

    @Override
    public void reset() throws IOException {
        if (expiry < System.currentTimeMillis()) {
            if (Files.getLastModifiedTime(keywordPath).toMillis() > lastModifed) {
                loadKeywordSet();
            }
            expiry = System.currentTimeMillis() + reloadInterval;
        }
        super.reset();
    }

    private void loadKeywordSet() {
        try (BufferedReader reader = Files.newBufferedReader(keywordPath, Charset.forName("UTF-8"))) {
            keywordSet = WordlistLoader.getWordSet(reader);
            lastModifed = Files.getLastModifiedTime(keywordPath).toMillis();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read " + keywordPath, e);
        }
    }

}
