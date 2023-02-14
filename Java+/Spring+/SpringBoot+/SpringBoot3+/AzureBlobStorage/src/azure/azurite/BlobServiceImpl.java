package azure.azurite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import util.InputStreamUtil;

import java.io.IOException;

@Service
class BlobServiceImpl implements BlobService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public String getBlobContent(String containerName, String fileName) throws IOException {
        var location = "azure-blob://" + containerName + "/" + fileName;
        var storageBlobResource = resourceLoader.getResource(location);
        return InputStreamUtil.inputStreamToString(storageBlobResource.getInputStream());
    }
}
