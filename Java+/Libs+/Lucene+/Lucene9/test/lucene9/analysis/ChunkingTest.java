package lucene9.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Imitates org.opensearch.neuralsearch.processor.chunker.FixedTokenLengthChunker
 */
class ChunkingTest {
    @Test
    void chunk() throws IOException {
        var text = "This is a sample text to demonstrate chunking by token length.";
        var chunkSize = 3;
        var chunks = chunkByTokenLength(text, chunkSize);
        assertThat(chunks).containsExactly(
                "this is a", "sample text to", "demonstrate chunking by", "token length");
    }

    public static List<String> chunkByTokenLength(String text, int chunkSize) throws IOException {
        List<String> chunks = new ArrayList<>();
        try (Analyzer analyzer = new StandardAnalyzer();
             var tokenStream = analyzer.tokenStream("text", new StringReader(text))) {
            var termAtt = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();

            var currentChunk = new StringBuilder();
            var tokenCount = 0;

            while (tokenStream.incrementToken()) {
                currentChunk.append(termAtt.toString()).append(" ");
                tokenCount++;

                if (tokenCount == chunkSize) {
                    chunks.add(currentChunk.toString().trim());
                    currentChunk.setLength(0); // Reset for the next chunk
                    tokenCount = 0;
                }
            }
            if (!currentChunk.isEmpty()) {
                chunks.add(currentChunk.toString().trim());
            }
        }
        return chunks;
    }

}
