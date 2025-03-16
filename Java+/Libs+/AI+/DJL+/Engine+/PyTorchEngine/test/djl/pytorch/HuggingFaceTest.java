package djl.pytorch;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class HuggingFaceTest {
    @Test
    void loadModel() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        Criteria<String, float[]> criteria =
                Criteria.builder()
                        .optApplication(Application.NLP.TEXT_EMBEDDING)
                        .setTypes(String.class, float[].class)
                        .optFilter("backbone", "sentence-transformers/paraphrase-mpnet-base-v2") // specify the model
                        .optEngine("PyTorch") // or TensorFlow based on availability
                        .build();

        try (ZooModel<String, float[]> model = ModelZoo.loadModel(criteria);
             Predictor<String, float[]> predictor = model.newPredictor()) {

            String sentence1 = "This is a sentence.";
            List<String> sentences = Arrays.asList(sentence1, "This is another sentence.");
            float[] embeddings = predictor.predict(sentence1);

            System.out.println(Arrays.toString(embeddings)); // Output will be a concatenated embedding for both sentences

            // If you need separate embeddings, predict individually:
            float[] embedding1 = predictor.predict(sentence1);
//            float[] embedding2 = predictor.predict(Arrays.asList(sentences.get(1)));

            System.out.println(Arrays.toString(embedding1));
//            System.out.println(Arrays.toString(embedding2));


            // Example of using tokenizer if needed for other tasks:
//            Vocabulary vocab = new DefaultVocabulary(model.getVocabulary());
//            BertTokenizer tokenizer = new BertTokenizer();
//
//            List<String> tokens = tokenizer.tokenize(sentences.get(0));
//            List<Long> tokenIds = vocab.encode(tokens);
//
//            System.out.println("Tokens: " + tokens);
//            System.out.println("Token IDs: " + tokenIds);

        }
    }
}
