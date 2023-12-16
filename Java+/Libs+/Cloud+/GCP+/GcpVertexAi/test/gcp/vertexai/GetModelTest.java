package gcp.vertexai;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.google.auth.oauth2.GoogleCredentials.fromStream;
import static util.FileUtil.homeDirFileToIS;

/**
 * DOES NOT WORK (requires OAuth credentials)
 */
class GetModelTest {
    @Test
    void get() throws IOException {
        var credentials = fromStream(homeDirFileToIS(".gcp-client", "api-client-408303-28254c1c5a46.json"));
        var projectId = "api-client-408303";
        var location = "us-central1";
        var modelName = "gemini-pro";
        try (var vertexAI = new VertexAI(projectId, location, credentials)) {
            var model = new GenerativeModel(modelName, vertexAI);
            var response = model.generateContent("How is Napoleon?");
            var output = response.toString();
            System.out.println(output);
        }

    }
}
