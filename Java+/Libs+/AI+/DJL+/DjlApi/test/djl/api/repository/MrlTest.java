package djl.api.repository;

import ai.djl.Application;
import ai.djl.repository.MRL;
import ai.djl.repository.Repository;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import static org.assertj.core.api.Assertions.assertThat;

class MrlTest {
    private final Repository repository = Repository.newInstance("my_repo", FileUtil.createTempDirectoryPath());
    private final String GROUP_ID = "group_id_1";
    private final String ARTIFACT_ID = "artifact_id_1";

    @Test
    void createModelMrl() {
        var mrl = MRL.model(repository, Application.NLP.TEXT_EMBEDDING, GROUP_ID, ARTIFACT_ID,
                "version_1", "artifact_name_1");
        assertThat(mrl).hasToString("model/nlp/text_embedding/group_id_1/artifact_id_1/");
    }

    @Test
    void createDatasetMrl() {
        var mrl = MRL.dataset(repository, Application.NLP.TEXT_EMBEDDING, GROUP_ID, ARTIFACT_ID, "version_1");
        assertThat(mrl).hasToString("dataset/nlp/text_embedding/group_id_1/artifact_id_1/");
    }

    @Test
    void createUndefinedMrl() {
        var mrl = MRL.undefined(repository, GROUP_ID, ARTIFACT_ID);
        assertThat(mrl).hasToString("undefined/group_id_1/artifact_id_1/");
    }

}