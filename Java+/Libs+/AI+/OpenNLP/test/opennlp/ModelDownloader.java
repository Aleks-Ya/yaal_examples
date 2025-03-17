package opennlp;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ModelDownloader {
    public static File downloadPosModel() throws IOException {
        var modelsDir = new File(System.getProperty("user.home"), ".opennpl");
        var modelFile = new File(modelsDir, "opennlp-en-ud-ewt-pos.bin");
        if (!modelFile.exists()) {
            if (!modelsDir.exists()) {
                if (!modelsDir.mkdirs()) {
                    throw new IllegalStateException("Cannot create model dir: " + modelsDir.getAbsolutePath());
                }
            }
            var modelUrl = new URL("https://dlcdn.apache.org/opennlp/models/ud-models-1.0/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin");
            FileUtils.copyURLToFile(modelUrl, modelFile);
        }
        return modelFile;
    }
}
