package djl.api.repository;

import ai.djl.training.util.DownloadUtils;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class DownloadUtilsTest {
    @Test
    void downloadModelToFile() throws IOException {
        var url = "https://huggingface.co/microsoft/MiniLM-L12-H384-uncased/resolve/main/pytorch_model.bin";
        var outputFile = FileUtil.createAbsentTempFile(".bin");
        var outputPath = outputFile.getAbsolutePath();
        System.out.println(outputPath);
        DownloadUtils.download(url, outputPath);
        System.out.println("Size: " + outputFile.length());
        assertThat(outputFile).exists().size().isGreaterThan(0);
    }
}