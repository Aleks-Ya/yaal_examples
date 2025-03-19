package djl;

import ai.djl.Application;
import ai.djl.repository.Artifact;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Helper {
    public static void printModels(Map<Application, List<Artifact>> models) {
        models.forEach((application, artifacts) ->
        {
            var applicationPath = application.getPath();
            var artifactList = artifacts.stream()
                    .map(artifact -> artifact.getName() + "-" + artifact.getVersion())
                    .toList();
            System.out.printf("\n%s: %s\n", applicationPath, artifactList);
        });
    }

    public static Map<String, List<String>> toMap(Map<Application, List<Artifact>> models) {
        return models.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getPath(),
                        entry -> entry.getValue().stream().map(Artifact::getName).toList())
                );
    }

}
