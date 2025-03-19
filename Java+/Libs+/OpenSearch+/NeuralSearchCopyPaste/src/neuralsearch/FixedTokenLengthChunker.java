package neuralsearch;


import org.opensearch.action.admin.indices.analyze.AnalyzeAction;
import org.opensearch.action.admin.indices.analyze.TransportAnalyzeAction;
import org.opensearch.index.analysis.AnalysisRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class FixedTokenLengthChunker implements Chunker {
    public static final String ANALYSIS_REGISTRY_FIELD = "analysis_registry";
    public static final String TOKEN_LIMIT_FIELD = "token_limit";
    public static final String OVERLAP_RATE_FIELD = "overlap_rate";
    public static final String MAX_TOKEN_COUNT_FIELD = "max_token_count";
    public static final String TOKENIZER_FIELD = "tokenizer";
    private static final Set<String> WORD_TOKENIZERS = Set.of("standard", "letter", "lowercase", "whitespace", "uax_url_email", "classic", "thai");
    private int tokenLimit;
    private String tokenizer;
    private double overlapRate;
    private final AnalysisRegistry analysisRegistry;

    public FixedTokenLengthChunker(Map<String, Object> parameters) {
        this.parseParameters(parameters);
        this.analysisRegistry = (AnalysisRegistry) parameters.get("analysis_registry");
    }

    public void parseParameters(Map<String, Object> parameters) {
        this.tokenLimit = ChunkerParameterParser.parsePositiveIntegerWithDefault(parameters, "token_limit", 384);
        this.overlapRate = ChunkerParameterParser.parseDoubleWithDefault(parameters, "overlap_rate", 0.0F);
        this.tokenizer = ChunkerParameterParser.parseStringWithDefault(parameters, "tokenizer", "standard");
        if (!(this.overlapRate < (double) 0.0F) && !(this.overlapRate > (double) 0.5F)) {
            if (!WORD_TOKENIZERS.contains(this.tokenizer)) {
                throw new IllegalArgumentException(String.format(Locale.ROOT, "Tokenizer [%s] is not supported for [%s] algorithm. Supported tokenizers are %s", this.tokenizer, "fixed_token_length", WORD_TOKENIZERS));
            }
        } else {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Parameter [%s] must be between %s and %s", "overlap_rate", (double) 0.0F, (double) 0.5F));
        }
    }

    public List<String> chunk(String content, Map<String, Object> runtimeParameters) {
        int maxTokenCount = ChunkerParameterParser.parseInteger(runtimeParameters, "max_token_count");
        int runtimeMaxChunkLimit = ChunkerParameterParser.parseInteger(runtimeParameters, "max_chunk_limit");
        int chunkStringCount = ChunkerParameterParser.parseInteger(runtimeParameters, "chunk_string_count");
        List<AnalyzeAction.AnalyzeToken> tokens = this.tokenize(content, this.tokenizer, maxTokenCount);
        List<String> chunkResult = new ArrayList<>();
        int startTokenIndex = 0;

        for (int overlapTokenNumber = (int) Math.floor((double) this.tokenLimit * this.overlapRate); startTokenIndex < tokens.size(); startTokenIndex += this.tokenLimit - overlapTokenNumber) {
            int startContentPosition;
            if (startTokenIndex == 0) {
                startContentPosition = 0;
            } else {
                startContentPosition = tokens.get(startTokenIndex).getStartOffset();
            }

            if (Chunker.checkRunTimeMaxChunkLimit(chunkResult.size(), runtimeMaxChunkLimit, chunkStringCount)) {
                chunkResult.add(content.substring(startContentPosition));
                break;
            }

            if (startTokenIndex + this.tokenLimit >= tokens.size()) {
                int endContentPosition = content.length();
                chunkResult.add(content.substring(startContentPosition, endContentPosition));
                break;
            }

            int endContentPosition = (tokens.get(startTokenIndex + this.tokenLimit)).getStartOffset();
            chunkResult.add(content.substring(startContentPosition, endContentPosition));
        }

        return chunkResult;
    }

    private List<AnalyzeAction.AnalyzeToken> tokenize(String content, String tokenizer, int maxTokenCount) {
        AnalyzeAction.Request analyzeRequest = new AnalyzeAction.Request();
        analyzeRequest.text(content);
        analyzeRequest.tokenizer(tokenizer);

        try {
            AnalyzeAction.Response analyzeResponse = TransportAnalyzeAction.analyze(analyzeRequest, this.analysisRegistry, null, maxTokenCount);
            return analyzeResponse.getTokens();
        } catch (Exception e) {
            throw new IllegalStateException(String.format(Locale.ROOT, "analyzer %s throws exception: %s", tokenizer, e.getMessage()), e);
        }
    }
}

