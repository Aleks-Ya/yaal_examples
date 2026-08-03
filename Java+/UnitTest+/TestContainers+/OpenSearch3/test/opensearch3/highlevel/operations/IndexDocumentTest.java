package opensearch3.highlevel.operations;

import opensearch3.OpenSearchExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opensearch.action.get.GetRequest;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;

import java.io.IOException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.opensearch.action.DocWriteResponse.Result.CREATED;
import static org.opensearch.client.RequestOptions.DEFAULT;
import static org.opensearch.common.xcontent.XContentType.JSON;

@ExtendWith(OpenSearchExtension.class)
class IndexDocumentTest {

    @Test
    void indexDocument(RestHighLevelClient client) throws IOException {
        var index = "index1";

        client.indices().create(new CreateIndexRequest(index), DEFAULT);

        var id = "1";
        var indexRequest = new IndexRequest(index).id(id).source("""
                {"title": "Document Title", "age": 30}""", JSON);
        var indexResponse = client.index(indexRequest, DEFAULT);
        assertThat(indexResponse.getResult()).isEqualTo(CREATED);

        var actJson = client.get(new GetRequest(index, id), DEFAULT).getSourceAsString();
        assertThatJson(actJson).isEqualTo("""
                {"title":"Document Title","age":30}""");
    }

}
