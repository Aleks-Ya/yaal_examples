package djl;

import ai.djl.MalformedModelException;
import ai.djl.huggingface.translator.TextEmbeddingTranslatorFactory;
import ai.djl.pytorch.engine.PtEngine;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;

import java.io.IOException;
import java.nio.file.Paths;

public class LocalModelLoader {
    public static final int PARAPHRASE_MP_NET_BASE_V_2_DIMENSION = 768;

    public static ZooModel<String, float[]> paraphraseMpNetBaseV2() throws ModelNotFoundException, MalformedModelException, IOException {
        var modelPath = Paths.get("/home/aleks/models/OpenSearch/torch/sentence-transformers_paraphrase-mpnet-base-v2-1.0.0-torch_script.zip");
        var translatorFactory = new TextEmbeddingTranslatorFactory();
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optTranslatorFactory(translatorFactory)
                .optModelPath(modelPath)
                .optEngine(PtEngine.ENGINE_NAME)
                .optModelName("paraphrase-mpnet-base-v2")
                .build();
        return criteria.loadModel();
    }

}
