package djl.api.repository;

import ai.djl.Application;
import ai.djl.repository.MRL;
import ai.djl.repository.Repository;
import ai.djl.repository.SimpleRepository;
import ai.djl.repository.zoo.DefaultModelZoo;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimpleRepositoryTest {

    @Test
    void newInstanceFromEmptyDir() {
        var localPath = FileUtil.createTempDirectoryPath();
        var repoName = "my_repo";
        var repository = Repository.newInstance(repoName, localPath);

        assertThat(repository).isNotNull();
        assertThat(repository).isInstanceOf(SimpleRepository.class);
        assertThat(repository.getName()).isEqualTo(repoName);
        assertThat(repository.isRemote()).isFalse();

        var resources = repository.getResources();
        assertThat(resources).hasSize(1);
        var resource = resources.getFirst();
        assertThat(resource.getApplication()).isEqualTo(Application.UNDEFINED);
        assertThat(resource.getGroupId()).isEqualTo(DefaultModelZoo.GROUP_ID);
        assertThat(resource.getArtifactId()).isEqualTo(localPath.getFileName().toString());
    }

    @Test
    void newInstanceFromDir() throws IOException {
        var localPath = FileUtil.createTempDirectoryPath();
        var dirName = localPath.getFileName().toString();
        var modelPath = localPath.resolve("model1.onnx");
        Files.writeString(modelPath, "model content");
        var repoName = "my_repo";
        var repository = Repository.newInstance(repoName, localPath);
        assertThat(repository).isInstanceOf(SimpleRepository.class);

        var mrl = repository.model(Application.NLP.TEXT_EMBEDDING, DefaultModelZoo.GROUP_ID, modelPath.getFileName().toString());
        assertThat(mrl).isNotNull();
        var artifact = mrl.getDefaultArtifact();
        assertThat(artifact).hasToString("ai.djl.localmodelzoo/%s/%s {}", dirName, dirName);
        var files = artifact.getFiles();
        assertThat(files).hasSize(1).containsKey("model1.onnx");
        var item = files.get("model1.onnx");
        assertThat(item.getArtifact()).isEqualTo(artifact);
    }

    @Test
    void addResource() {
        var localPath = FileUtil.createTempDirectoryPath();
        var repository = Repository.newInstance("my_repo", localPath);
        var resource = MRL.undefined(repository, "my_group", "my_artifact");
        assertThatThrownBy(() -> repository.addResource(resource))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("SimpleRepository doesn't support addResource.");
    }

    @Test
    void newInstanceFromOnnxFile() {
        var localPath = Paths.get("/home/aleks/models/OpenSearch/sentence-transformers_msmarco-distilbert-base-tas-b-1.0.2-onnx/msmarco-distilbert-base-tas-b.onnx");
        var repoName = "my_repo";
        var repository = Repository.newInstance(repoName, localPath);

        assertThat(repository).isNotNull();
        assertThat(repository).isInstanceOf(SimpleRepository.class);
        assertThat(repository.getName()).isEqualTo(repoName);
        assertThat(repository.isRemote()).isFalse();

        var modelMrl = repository.model(Application.UNDEFINED, DefaultModelZoo.GROUP_ID, localPath.getFileName().toString());
        assertThat(modelMrl).isNotNull();
    }
}