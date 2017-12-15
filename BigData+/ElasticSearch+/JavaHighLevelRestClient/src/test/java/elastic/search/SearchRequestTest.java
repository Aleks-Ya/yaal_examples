package elastic.search;

import elastic.ConnectionHelper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class SearchRequestTest {
    private static final String PEOPLE_INDEX = "people";
    private static final String PERSONS_TYPE = "persons";
    private static final String EMAIL_FILED = "email";
    private static final String JOHN_EMAIL = "john@mail.ru";
    private static final String MARY_MAIL = "mary@mail.ru";
    private static final RestHighLevelClient client = ConnectionHelper.getHighLevelRestClient();

    @Test
    public void matchAllQuery() throws IOException {
        MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);

        SearchRequest request = new SearchRequest();
        request.indices(PEOPLE_INDEX);
        request.types(PERSONS_TYPE);
        request.source(searchSourceBuilder);

        String[] includeFields = new String[]{"name", "age", "companyId"};
        String[] excludeFields = new String[]{EMAIL_FILED};
        searchSourceBuilder.fetchSource(includeFields, excludeFields);

        SearchResponse response = client.search(request);

        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(4L));

        SearchHits searchHits = response.getHits();
        SearchHit hit0 = searchHits.getAt(0);

        assertThat(hit0, notNullValue());
    }

    @Test
    public void termQuery() throws IOException {
        TermQueryBuilder query = QueryBuilders.termQuery(EMAIL_FILED, JOHN_EMAIL);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);

        SearchRequest request = new SearchRequest();
        request.indices(PEOPLE_INDEX);
        request.types(PERSONS_TYPE);
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request);

        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(1L));

        SearchHits searchHits = response.getHits();
        SearchHit hit0 = searchHits.getAt(0);

        assertThat(hit0.getSource().get(EMAIL_FILED), equalTo(JOHN_EMAIL));
    }

    @Test
    public void boolQueryShould() throws IOException {
        TermQueryBuilder query1 = QueryBuilders.termQuery(EMAIL_FILED, JOHN_EMAIL);

        TermQueryBuilder query2 = QueryBuilders.termQuery(EMAIL_FILED, MARY_MAIL);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.should().add(query1);
        boolQuery.should().add(query2);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQuery);

        SearchRequest request = new SearchRequest();
        request.indices(PEOPLE_INDEX);
        request.types(PERSONS_TYPE);
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request);

        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(2L));

        SearchHits searchHits = response.getHits();
        SearchHit hit0 = searchHits.getAt(0);
        SearchHit hit1 = searchHits.getAt(1);

        assertThat(hit0.getSource().get(EMAIL_FILED), anyOf(equalTo(JOHN_EMAIL), equalTo(MARY_MAIL)));
        assertThat(hit1.getSource().get(EMAIL_FILED), anyOf(equalTo(JOHN_EMAIL), equalTo(MARY_MAIL)));
    }

    @Test
    public void count() throws IOException {
        TermQueryBuilder query = QueryBuilders.termQuery(EMAIL_FILED, JOHN_EMAIL);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.fetchSource(false);//exclude source

        SearchRequest request = new SearchRequest();
        request.indices(PEOPLE_INDEX);
        request.types(PERSONS_TYPE);
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request);

        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(1L));
    }
}
