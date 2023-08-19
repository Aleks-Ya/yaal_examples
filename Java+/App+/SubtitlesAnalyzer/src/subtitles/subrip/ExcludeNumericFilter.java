package subtitles.subrip;

import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

class ExcludeNumericFilter extends FilteringTokenFilter {
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    public ExcludeNumericFilter(TokenStream in) {
        super(in);
    }

    @Override
    protected boolean accept() {
        return !termAtt.toString().matches(".*[\\d'.]+.*");
    }
}