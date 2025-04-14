package djl;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalModels {
    private static final Path MODELS_PATH = Paths.get(System.getProperty("user.home"), "models");
    private static final Path PARAPHRASE_MPNET_BASE_V2 = pathExists(MODELS_PATH,
            "OpenSearch", "paraphrase-mpnet-base-v2-1.0.0-onnx");

    public static class OpenSearch {
        public static final Path PARAPHRASE_MPNET_BASE_V2_ZIP = pathExists(PARAPHRASE_MPNET_BASE_V2,
                "sentence-transformers_paraphrase-mpnet-base-v2-1.0.0-onnx.zip");
        public static final Path PARAPHRASE_MPNET_BASE_V2_ONNX = pathExists(PARAPHRASE_MPNET_BASE_V2,
                "sentence-transformers_paraphrase-mpnet-base-v2-1.0.0-onnx",
                "paraphrase-mpnet-base-v2.onnx");
        public static final Path ALL_MPNET_BASE_V2_ZIP = pathExists(MODELS_PATH,
                "OpenSearch", "all-mpnet-base-v2-1.0.1-onnx",
                "sentence-transformers_all-mpnet-base-v2-1.0.1-onnx.zip");
    }

    private static Path pathExists(Path parent, String... subfolders) {
        var path = parent;
        for (var subfolder : subfolders) {
            path = path.resolve(subfolder);
        }
        if (!path.toFile().exists()) {
            throw new IllegalStateException("Path does not exist: " + path.toAbsolutePath());
        }
        return path;
    }
}
