package neuralsearch.processor.text;

import org.junit.jupiter.api.Test;
import org.opensearch.client.Client;
import org.opensearch.client.node.NodeClient;
import org.opensearch.common.settings.Settings;
import org.opensearch.core.action.ActionListener;
import org.opensearch.ml.client.MachineLearningNodeClient;
import org.opensearch.neuralsearch.ml.MLCommonsClientAccessor;
import org.opensearch.threadpool.FixedExecutorBuilder;
import org.opensearch.threadpool.ThreadPool;

import java.util.List;

class TextEmbeddingTest {
    @Test
    void embeddings() {
        var modelId = "huggingface/sentence-transformers/msmarco-distilbert-base-tas-b";
        var inputText = List.of("OpenSearch is a highly scalable open-source search and analytics suite.");
        ActionListener<List<List<Float>>> listener = ActionListener.wrap(System.out::println, System.out::println);
        var settings = Settings.builder()
                .build();
        var executorBuilder = new FixedExecutorBuilder(settings, "pool1", 1, 1, "prefix1");
        var threadPool = new ThreadPool(settings, executorBuilder);
        Client client = new NodeClient(settings, threadPool);
        var mlClient = new MachineLearningNodeClient(client);
        var mlAccessor = new MLCommonsClientAccessor(mlClient);
        mlAccessor.inferenceSentences(modelId, inputText, listener);
    }

}
