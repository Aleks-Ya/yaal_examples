package neuralsearch.processor.text;

import org.junit.jupiter.api.Test;
import org.opensearch.common.settings.Settings;
import org.opensearch.env.Environment;
import org.opensearch.indices.analysis.AnalysisModule;
import org.opensearch.neuralsearch.processor.chunker.FixedTokenLengthChunker;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.opensearch.env.Environment.PATH_HOME_SETTING;

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
                "analysis_registry", analysisRegistry,
                "token_limit", 6,
                "overlap_rate", 0.2,
                "tokenizer", "standard"
        ));
        var runtimeParameters = Map.<String, Object>of(
                "max_token_count", 1000,
                "max_chunk_limit", -1,
                "chunk_string_count", 1
        );
        var chunks = chunker.chunk(content, runtimeParameters);
        System.out.println(chunks);
        assertThat(chunks).containsExactly(
                "OpenSearch is a highly scalable open-",
                "open-source search and analytics suite.");
    }

}
