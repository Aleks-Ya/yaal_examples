package elastic.search;

import elastic.ConnectionHelper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SearchRequestTest {
    @Test
    public void searchSourceBuilderMatchAll() throws IOException {
        RestClient lowLevelRestClient = ConnectionHelper.getLowLevelRestClient();
        RestHighLevelClient client = new RestHighLevelClient(lowLevelRestClient);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        String index = "people";
        String type = "persons";
        SearchRequest request = new SearchRequest();
        request.indices(index);
        request.types(type);
        request.source(searchSourceBuilder);

        String[] includeFields = new String[]{"name", "age", "companyId"};
        String[] excludeFields = new String[]{"email"};
        searchSourceBuilder.fetchSource(includeFields, excludeFields);

        SearchResponse response = client.search(request);

        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(4L));

        SearchHits searchHits = response.getHits();
        SearchHit hit0 = searchHits.getAt(0);

        assertThat(hit0, notNullValue());
    }

}
