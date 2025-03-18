package djl.api.repository;

import ai.djl.repository.LocalRepository;
import ai.djl.repository.MRL;
import ai.djl.repository.Repository;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalRepositoryTest {
    private final String repoName = "my_repo";

    @Test
    void newInstance() throws IOException {
        var repository = createEmptyRepo();
        assertThat(repository).isNotNull();
        assertThat(repository).isInstanceOf(LocalRepository.class);
        assertThat(repository.getName()).isEqualTo(repoName);
        assertThat(repository.isRemote()).isFalse();
        assertThat(repository.getResources()).isEmpty();
    }

    @Test
    void addResource() throws IOException {
        var repository = createEmptyRepo();
        var resource = MRL.undefined(repository, "my_group", "my_artifact");
        assertThatThrownBy(() -> repository.addResource(resource))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("LocalRepository doesn't support addResource.");
    }

    private Repository createEmptyRepo() throws IOException {
        var reposPath = FileUtil.createTempDirectoryPath();
        var repoPath = reposPath.resolve(repoName);
        Files.createDirectory(repoPath);
        var metadataFile = repoPath.resolve("metadata.json");
        var metadataContent = """
                {
                    "metadataVersion": "0.0.1",
                    "artifacts": []
                }
                """;
        Files.writeString(metadataFile, metadataContent);
        return Repository.newInstance(repoName, reposPath);
    }
}