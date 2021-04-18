package elastic.search;

import elastic.EsHelper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SearchRequestTest {
    private static final String PEOPLE_INDEX = "people";
    private static final String PERSONS_TYPE = "persons";
    private static final String EMAIL_FILED = "email";
    private static final String JOHN_EMAIL = "john@mail.ru";
    private static final String MARY_MAIL = "mary@mail.ru";
    private static final RestHighLevelClient client = EsHelper.getHighLevelRestClient();

    @Test
    public void matchAllQuery() throws IOException {
        var query = QueryBuilders.matchAllQuery();

        var searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);

        var request = new SearchRequest();
        request.indices(PEOPLE_INDEX);
        request.types(PERSONS_TYPE);
        request.source(searchSourceBuilder);

        var includeFields = new String[]{"name", "age", "companyId"};
        var excludeFields = new String[]{EMAIL_FILED};
        searchSourceBuilder.fetchSource(includeFields, excludeFields);

        var response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(4L));

        var searchHits = response.getHits();
        var hit0 = searchHits.getAt(0);

        assertThat(hit0, notNullValue());
    }

    @Test
    public void termQuery() throws IOException {
        var query = QueryBuilders.termQuery(EMAIL_FILED, JOHN_EMAIL);

        var searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);

        var request = new SearchRequest();
        request.indices(PEOPLE_INDEX);
        request.types(PERSONS_TYPE);
        request.source(searchSourceBuilder);

        var response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(1L));

        var searchHits = response.getHits();
        var hit0 = searchHits.getAt(0);

        assertThat(hit0.getSourceAsMap().get(EMAIL_FILED), equalTo(JOHN_EMAIL));
    }

    @Test
    public void boolQueryShould() throws IOException {
        var query1 = QueryBuilders.termQuery(EMAIL_FILED, JOHN_EMAIL);

        var query2 = QueryBuilders.termQuery(EMAIL_FILED, MARY_MAIL);

        var boolQuery = QueryBuilders.boolQuery();
        boolQuery.should().add(query1);
        boolQuery.should().add(query2);

        var searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQuery);

        var request = new SearchRequest();
        request.indices(PEOPLE_INDEX);
        request.types(PERSONS_TYPE);
        request.source(searchSourceBuilder);

        var response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(2L));

        var searchHits = response.getHits();
        var hit0 = searchHits.getAt(0);
        var hit1 = searchHits.getAt(1);

        assertThat(hit0.getSourceAsMap().get(EMAIL_FILED), anyOf(equalTo(JOHN_EMAIL), equalTo(MARY_MAIL)));
        assertThat(hit1.getSourceAsMap().get(EMAIL_FILED), anyOf(equalTo(JOHN_EMAIL), equalTo(MARY_MAIL)));
    }

    @Test
    public void count() throws IOException {
        var query = QueryBuilders.termQuery(EMAIL_FILED, JOHN_EMAIL);

        var searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.fetchSource(false);//exclude source

        var request = new SearchRequest();
        request.indices(PEOPLE_INDEX);
        request.types(PERSONS_TYPE);
        request.source(searchSourceBuilder);

        var response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(response);

        assertThat(response.status().getStatus(), equalTo(200));
        assertThat(response.getHits().getTotalHits(), equalTo(1L));
    }
}
