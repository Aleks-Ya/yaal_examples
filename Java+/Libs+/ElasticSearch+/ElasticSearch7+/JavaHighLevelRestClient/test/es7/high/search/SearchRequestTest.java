package es7.high.search;

import es7.high.EsHelper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class SearchRequestTest {
    private static final String PEOPLE_INDEX = "people";
    private static final String PERSONS_TYPE = "persons";
    private static final String EMAIL_FILED = "email";
    private static final String JOHN_EMAIL = "john@mail.ru";
    private static final String MARY_MAIL = "mary@mail.ru";
    private static final RestHighLevelClient client = EsHelper.getHighLevelRestClient();

    @Test
    void matchAllQuery() throws IOException {
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

        assertThat(response.status().getStatus()).isEqualTo(200);
        assertThat(response.getHits().getTotalHits().value).isEqualTo(4L);

        var searchHits = response.getHits();
        var hit0 = searchHits.getAt(0);

        assertThat(hit0).isNotNull();
    }

    @Test
    void termQuery() throws IOException {
        var query = QueryBuilders.termQuery(EMAIL_FILED, JOHN_EMAIL);

        var searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);

        var request = new SearchRequest();
        request.indices(PEOPLE_INDEX);
        request.types(PERSONS_TYPE);
        request.source(searchSourceBuilder);

        var response = client.search(request, RequestOptions.DEFAULT);

        System.out.println(response);

        assertThat(response.status().getStatus()).isEqualTo(200);
        assertThat(response.getHits().getTotalHits().value).isEqualTo(1L);

        var searchHits = response.getHits();
        var hit0 = searchHits.getAt(0);

        assertThat(hit0.getSourceAsMap().get(EMAIL_FILED)).isEqualTo(JOHN_EMAIL);
    }

    @Test
    void boolQueryShould() throws IOException {
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

        assertThat(response.status().getStatus()).isEqualTo(200);
        assertThat(response.getHits().getTotalHits().value).isEqualTo(2L);

        var searchHits = response.getHits();
        var hit0 = searchHits.getAt(0);
        var hit1 = searchHits.getAt(1);

        var email0 = hit0.getSourceAsMap().get(EMAIL_FILED);
        var email1 = hit1.getSourceAsMap().get(EMAIL_FILED);
        assertThat(JOHN_EMAIL.equals(email0) || MARY_MAIL.equals(email0)).isTrue();
        assertThat(JOHN_EMAIL.equals(email1) || MARY_MAIL.equals(email1)).isTrue();
    }

    @Test
    void count() throws IOException {
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

        assertThat(response.status().getStatus()).isEqualTo(200);
        assertThat(response.getHits().getTotalHits().value).isEqualTo(1L);
    }
}
