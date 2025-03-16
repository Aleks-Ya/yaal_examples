package djl.zoo;

import ai.djl.Application;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.repository.Artifact;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class ListModelsTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = ModelZoo.listModels();
        printModels(models);
    }

    @Test
    void listModelsByCriteria() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .build();
        var models = ModelZoo.listModels(criteria);
        printModels(models);
    }

    private static void printModels(Map<Application, List<Artifact>> models) {
        models.forEach((application, artifacts) ->
        {
            var applicationPath = application.getPath();
            var artifactList = artifacts.stream()
                    .map(artifact -> artifact.getName() + "-" + artifact.getVersion())
                    .toList();
            System.out.printf("\n%s: %s\n", applicationPath, artifactList);
        });
    }

}
