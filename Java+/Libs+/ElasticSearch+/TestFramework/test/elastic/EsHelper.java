package elastic;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.client.Client;

import java.util.Random;

public final class EsHelper {
    private static final Random random = new Random();

    private EsHelper() {
    }

    public static String createRandomIndexName() {
        return String.format("%s-%d", EsHelper.class.getSimpleName(), random.nextInt(Integer.MAX_VALUE)).toLowerCase();
    }

    public static boolean isIndexExist(String indexName, Client client) {
        return client.admin().indices().exists(new IndicesExistsRequest(indexName)).actionGet().isExists();
    }
}
