package subtitles.subrip;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.en.EnglishMinimalStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

class ExcludeNumbersAnalyzer extends Analyzer {

    private final CharArraySet stopWords;

    public ExcludeNumbersAnalyzer(CharArraySet stopWords) {
        this.stopWords = stopWords;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        var tokenizer = new StandardTokenizer();
        TokenFilter tokenStream = new ExcludeNumericFilter(tokenizer);
        tokenStream = new LowerCaseFilter(tokenStream);
        tokenStream = new EnglishMinimalStemFilter(tokenStream);
        tokenStream = new StopFilter(tokenStream, stopWords);
        return new TokenStreamComponents(tokenizer, tokenStream);
    }
}
