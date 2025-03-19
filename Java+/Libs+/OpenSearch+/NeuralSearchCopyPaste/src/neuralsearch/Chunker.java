package neuralsearch;

import java.util.List;
import java.util.Map;

public interface Chunker {
    String MAX_CHUNK_LIMIT_FIELD = "max_chunk_limit";
    String CHUNK_STRING_COUNT_FIELD = "chunk_string_count";

    void parseParameters(Map<String, Object> var1);

    List<String> chunk(String var1, Map<String, Object> var2);

    static boolean checkRunTimeMaxChunkLimit(int chunkResultSize, int runtimeMaxChunkLimit, int chunkStringCount) {
        return runtimeMaxChunkLimit != -1 && chunkResultSize + chunkStringCount >= runtimeMaxChunkLimit;
    }
}
