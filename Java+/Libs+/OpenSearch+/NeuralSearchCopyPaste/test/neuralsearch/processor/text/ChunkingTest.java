package neuralsearch.processor.text;

import org.junit.jupiter.api.Test;
import org.opensearch.indices.analysis.AnalysisModule;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static neuralsearch.Chunker.CHUNK_STRING_COUNT_FIELD;
import static neuralsearch.Chunker.MAX_CHUNK_LIMIT_FIELD;
import static neuralsearch.FixedTokenLengthChunker.ANALYSIS_REGISTRY_FIELD;
import static neuralsearch.FixedTokenLengthChunker.MAX_TOKEN_COUNT_FIELD;
import static neuralsearch.FixedTokenLengthChunker.OVERLAP_RATE_FIELD;
import static neuralsearch.FixedTokenLengthChunker.TOKENIZER_FIELD;
import static neuralsearch.FixedTokenLengthChunker.TOKEN_LIMIT_FIELD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.opensearch.env.Environment.PATH_HOME_SETTING;
import neuralsearch.FixedTokenLengthChunker;
import org.opensearch.env.Environment;
import org.opensearch.common.settings.Settings;

class ChunkingTest {
    @Test
    void chunkString() throws IOException {
        var content = "OpenSearch is a highly scalable open-source search and analytics suite.";
        var homePath = Paths.get("/tmp/chunking/home");
        var configPath = Paths.get("/tmp/chunking/config");
        var settings = Settings.builder()
                .put(PATH_HOME_SETTING.getKey(), homePath)
                .build();
        var environment = new Environment(settings, configPath);
        var analysisModule = new AnalysisModule(environment, List.of());
        var analysisRegistry = analysisModule.getAnalysisRegistry();
        var chunker = new FixedTokenLengthChunker(Map.of(
                ANALYSIS_REGISTRY_FIELD, analysisRegistry,
                TOKEN_LIMIT_FIELD, 6,
                OVERLAP_RATE_FIELD, 0.2,
                TOKENIZER_FIELD, "standard"
        ));
        var runtimeParameters = Map.<String, Object>of(
                MAX_TOKEN_COUNT_FIELD, 1000,
                MAX_CHUNK_LIMIT_FIELD, 100,
                CHUNK_STRING_COUNT_FIELD, 1
        );
        var chunks = chunker.chunk(content, runtimeParameters);
        System.out.println(chunks);
        assertThat(chunks).containsExactly(
                "OpenSearch is a highly scalable open-",
                "open-source search and analytics suite.");
    }

}
