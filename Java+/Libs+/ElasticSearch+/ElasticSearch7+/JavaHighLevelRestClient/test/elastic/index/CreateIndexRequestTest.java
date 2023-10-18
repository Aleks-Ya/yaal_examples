package elastic.index;

import org.junit.jupiter.api.Test;

import static elastic.EsHelper.createIndex;
import static elastic.EsHelper.createRandomIndexName;
import static elastic.EsHelper.deleteIndex;
import static elastic.EsHelper.isIndexExist;
import static org.assertj.core.api.Assertions.assertThat;

class CreateIndexRequestTest {
    @Test
    void createAndDeleteIndex() {
        var indexName = createRandomIndexName();
        assertThat(isIndexExist(indexName)).isFalse();
        assertThat(createIndex(indexName).isAcknowledged()).isTrue();
        assertThat(isIndexExist(indexName)).isTrue();
        assertThat(deleteIndex(indexName).isAcknowledged()).isTrue();
        assertThat(isIndexExist(indexName)).isFalse();
    }
}
