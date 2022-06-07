package elastic;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.client.Client;
import util.RandomUtil;

public final class EsHelper {
    private EsHelper() {
    }

    public static String createRandomIndexName() {
        return String.format("%s-%d", EsHelper.class.getSimpleName(), RandomUtil.randomIntPositive()).toLowerCase();
    }

    public static boolean isIndexExist(String indexName, Client client) {
        return client.admin().indices().exists(new IndicesExistsRequest(indexName)).actionGet().isExists();
    }
}
