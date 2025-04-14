package djl.onnxrs.load.zoo;

import ai.djl.Application;
import ai.djl.onnxruntime.engine.OrtEngine;
import ai.djl.onnxruntime.zoo.OrtHfModelZoo;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HuggingFace models for ONNX.
 */
class OrtHfModelZooTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = OrtHfModelZoo.listModels();
        Helper.printModels(models);
    }

    @Test
    void getModelZoo() {
        var zoo = ModelZoo.getModelZoo("ai.djl.huggingface.onnxruntime");//constant OrtHfModelZoo.GROUP_ID is private
        var engines = zoo.getSupportedEngines();
        assertThat(engines).containsOnly(OrtEngine.ENGINE_NAME);
    }

    @Test
    void getTextEmbeddingModels() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .optApplication(Application.NLP.TEXT_EMBEDDING)
                .build();
        var models = ModelZoo.listModels(criteria);
        Helper.printModels(models);
        assertThat(models).containsOnlyKeys(Application.NLP.TEXT_EMBEDDING);
    }

    @Test
    void getModelByName() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .optModelName("resnet18_v1-7") //NOT WORKING: returns all models (field "Criteria.getModelName" is ignored)
                .build();
        var models = ModelZoo.listModels(criteria);
        Helper.printModels(models);
    }

    @Test
    void findModelsByName() throws ModelNotFoundException, IOException {
        var modelName = "resnet18_v1-7";
        var models = ModelZoo.listModels().entrySet().stream()
                .map(entry -> Map.entry(
                        entry.getKey(),
                        entry.getValue().stream()
                                .filter(artifact -> artifact
                                        .getName()
                                        .toLowerCase()
                                        .contains(modelName.toLowerCase()))
                                .toList()
                ))
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Helper.printModels(models);
    }

    @Test
    void getModelByArtifactId() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .optArtifactId("resnet")
                .build();
        var models = ModelZoo.listModels(criteria);
        Helper.printModels(models);
    }
}
