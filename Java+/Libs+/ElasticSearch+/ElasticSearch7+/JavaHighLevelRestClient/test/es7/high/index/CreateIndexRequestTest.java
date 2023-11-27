package es7.high.index;

import org.junit.jupiter.api.Test;

import static es7.high.EsHelper.createIndex;
import static es7.high.EsHelper.createRandomIndexName;
import static es7.high.EsHelper.deleteIndex;
import static es7.high.EsHelper.isIndexExist;
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
