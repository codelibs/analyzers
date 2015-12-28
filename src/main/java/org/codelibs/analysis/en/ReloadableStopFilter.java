package org.codelibs.analysis.en;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.FilteringTokenFilter;
import org.apache.lucene.analysis.util.WordlistLoader;

public class ReloadableStopFilter extends FilteringTokenFilter {

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    private CharArraySet stopWords;

    private final Path stopWordPath;

    private long reloadInterval;

    private long expiry;

    private long lastModifed;

    public ReloadableStopFilter(TokenStream in, Path stopwordPath, long reloadInterval) {
        super(in);
        this.stopWordPath = stopwordPath;
        this.reloadInterval = reloadInterval;

        loadStopWordSet();
        expiry = System.currentTimeMillis() + reloadInterval;
    }

    @Override
    protected boolean accept() {
        return !stopWords.contains(termAtt.buffer(), 0, termAtt.length());
    }

    @Override
    public void reset() throws IOException {
        if (expiry < System.currentTimeMillis()) {
            if (Files.getLastModifiedTime(stopWordPath).toMillis() > lastModifed) {
                loadStopWordSet();
            }
            expiry = System.currentTimeMillis() + reloadInterval;
        }
        super.reset();
    }

    private void loadStopWordSet() {
        try (BufferedReader reader = Files.newBufferedReader(stopWordPath, Charset.forName("UTF-8"))) {
            stopWords = WordlistLoader.getWordSet(reader);
            lastModifed = Files.getLastModifiedTime(stopWordPath).toMillis();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read " + stopWordPath, e);
        }
    }

}
