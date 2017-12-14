package elastic.search;

import elastic.ConnectionHelper;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@Ignore("RestClient from Elastic v5 doesn't support MultiSearchRequest (need v6)")
public class MiltiSearchRequestTest {
    @Test
    public void matchAllQuery() throws IOException {
        RestClient lowLevelRestClient = ConnectionHelper.getLowLevelRestClient();
        RestHighLevelClient client = new RestHighLevelClient(lowLevelRestClient);



        MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);

        String index = "people";
        String type = "persons";

        MultiSearchRequest multiSearchRequest = new MultiSearchRequest();

//        MultiSearchAction multiSearchAction = new
//        MultiSearchRequestBuilder multiSearchRequestBuilder = new MultiSearchRequestBuilder();


        SearchRequest request1 = new SearchRequest();
        request1.indices(index);
        request1.types(type);
        request1.source(searchSourceBuilder);

        String[] includeFields = new String[]{"name", "age", "companyId"};
        String[] excludeFields = new String[]{"email"};
        searchSourceBuilder.fetchSource(includeFields, excludeFields);

        SearchResponse response = client.search(request1);


        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(4L));

        SearchHits searchHits = response.getHits();
        SearchHit hit0 = searchHits.getAt(0);

        assertThat(hit0, notNullValue());
    }

    @Test
    public void termQuery() throws IOException {
        RestClient lowLevelRestClient = ConnectionHelper.getLowLevelRestClient();
        RestHighLevelClient client = new RestHighLevelClient(lowLevelRestClient);

        String email = "john@mail.ru";
        String field = "email";
        TermQueryBuilder query = QueryBuilders.termQuery(field, email);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);

        String index = "people";
        String type = "persons";
        SearchRequest request = new SearchRequest();
        request.indices(index);
        request.types(type);
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request);

        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(1L));

        SearchHits searchHits = response.getHits();
        SearchHit hit0 = searchHits.getAt(0);

        assertThat(hit0.getSource().get(field), equalTo(email));
    }

    @Test
    public void boolQueryShould() throws IOException {
        RestClient lowLevelRestClient = ConnectionHelper.getLowLevelRestClient();
        RestHighLevelClient client = new RestHighLevelClient(lowLevelRestClient);


        String email1 = "john@mail.ru";
        String field = "email";
        TermQueryBuilder query1 = QueryBuilders.termQuery(field, email1);

        String email2 = "mary@mail.ru";
        TermQueryBuilder query2 = QueryBuilders.termQuery(field, email2);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.should().add(query1);
        boolQuery.should().add(query2);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQuery);

        String index = "people";
        String type = "persons";
        SearchRequest request = new SearchRequest();
        request.indices(index);
        request.types(type);
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request);

        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(2L));

        SearchHits searchHits = response.getHits();
        SearchHit hit0 = searchHits.getAt(0);
        SearchHit hit1 = searchHits.getAt(1);

        assertThat(hit0.getSource().get(field), anyOf(equalTo(email1), equalTo(email2)));
        assertThat(hit1.getSource().get(field), anyOf(equalTo(email1), equalTo(email2)));
    }

}
