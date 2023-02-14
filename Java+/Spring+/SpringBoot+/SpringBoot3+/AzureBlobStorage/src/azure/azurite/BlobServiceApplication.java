package azure.azurite;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class BlobServiceApplication {

    public static void main(String[] args) throws IOException {
        try (var context = new AnnotationConfigApplicationContext("azure.testing")) {
            var fileName = "data.txt";
            var containerName = "container1";
            var blobService = context.getBean(BlobService.class);
            var actContent = blobService.getBlobContent(containerName, fileName);
            assert "abc".equals(actContent);
        }
    }

}
