package elastic.search;

import elastic.EsHelper;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Disabled("RestClient from Elastic v5 doesn't support MultiSearchRequest (need v6)")
public class MiltiSearchRequestTest {
    @Test
    public void matchAllQuery() throws IOException {
        var client = EsHelper.getHighLevelRestClient();

        var query = QueryBuilders.matchAllQuery();

        var searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);

        var index = "people";
        var type = "persons";

        var multiSearchRequest = new MultiSearchRequest();

//        MultiSearchAction multiSearchAction = new
//        MultiSearchRequestBuilder multiSearchRequestBuilder = new MultiSearchRequestBuilder();


        var request1 = new SearchRequest();
        request1.indices(index);
        request1.types(type);
        request1.source(searchSourceBuilder);

        var includeFields = new String[]{"name", "age", "companyId"};
        var excludeFields = new String[]{"email"};
        searchSourceBuilder.fetchSource(includeFields, excludeFields);

        var response = client.search(request1, RequestOptions.DEFAULT);


        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(4L));

        var searchHits = response.getHits();
        var hit0 = searchHits.getAt(0);

        assertThat(hit0, notNullValue());
    }

}
