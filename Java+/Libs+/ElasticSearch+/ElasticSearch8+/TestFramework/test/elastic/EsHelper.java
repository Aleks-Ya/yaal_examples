package elastic;

import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.internal.Client;
import util.RandomUtil;

public final class EsHelper {
    private EsHelper() {
    }

    public static String createRandomIndexName() {
        return String.format("%s-%d", EsHelper.class.getSimpleName(), RandomUtil.randomIntPositive()).toLowerCase();
    }

    public static boolean isIndexExist(String indexName, Client client) {
        var getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(indexName);
        var indices = client.admin().indices().getIndex(getIndexRequest).actionGet().indices();
        return indices.length > 0;
    }
}
