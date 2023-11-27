package es8.testframework;

import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.internal.Client;
import util.RandomUtil;

import static org.elasticsearch.action.support.IndicesOptions.LENIENT_EXPAND_OPEN;

public final class EsHelper {
    private EsHelper() {
    }

    public static String createRandomIndexName() {
        return String.format("%s-%d", EsHelper.class.getSimpleName(), RandomUtil.randomIntPositive()).toLowerCase();
    }

    public static boolean isIndexExist(String indexName, Client client) {
        var request = new GetIndexRequest().indices(indexName).indicesOptions(LENIENT_EXPAND_OPEN);
        var indices = client.admin().indices().getIndex(request).actionGet().indices();
        return indices.length > 0;
    }
}
