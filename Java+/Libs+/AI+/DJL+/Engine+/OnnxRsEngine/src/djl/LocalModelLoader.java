package djl;

import ai.djl.MalformedModelException;
import ai.djl.huggingface.translator.TextEmbeddingTranslatorFactory;
import ai.djl.onnxruntime.engine.OrtEngine;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;

import java.io.IOException;

public class LocalModelLoader {
    public static final int DIMENSION_768 = 768;

    public static ZooModel<String, float[]> paraphraseMpNetBaseV2() throws ModelNotFoundException, MalformedModelException, IOException {
        var translatorFactory = new TextEmbeddingTranslatorFactory();
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optTranslatorFactory(translatorFactory)
                .optModelPath(LocalModels.OpenSearch.PARAPHRASE_MPNET_BASE_V2_ZIP)
                .optEngine(OrtEngine.ENGINE_NAME)
                .optModelName("paraphrase-mpnet-base-v2")
                .build();
        return criteria.loadModel();
    }

    public static ZooModel<String, float[]> allMpNetBaseV2() throws ModelNotFoundException, MalformedModelException, IOException {
        var translatorFactory = new TextEmbeddingTranslatorFactory();
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optTranslatorFactory(translatorFactory)
                .optModelPath(LocalModels.OpenSearch.ALL_MPNET_BASE_V2_ZIP)
                .optEngine(OrtEngine.ENGINE_NAME)
                .optModelName("all-mpnet-base-v2")
                .build();
        return criteria.loadModel();
    }

}
