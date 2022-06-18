package azure.azurite;

import java.io.IOException;

interface BlobService {
    String getBlobContent(String containerName, String fileName) throws IOException;
}
