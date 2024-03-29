package blob.real;

import blob.Factory;
import com.azure.storage.blob.models.BlobContainerItem;
import org.junit.jupiter.api.Test;
import util.RandomUtil;

import static org.assertj.core.api.Assertions.assertThat;

class ContainerTest {
    @Test
    void createListDeleteContainer() {
        var client = Factory.realBlobServiceClient();
        var container = getClass().getSimpleName().toLowerCase() + RandomUtil.randomIntPositive();
        client.createBlobContainer(container);
        var containers = client.listBlobContainers();
        assertThat(containers.stream().map(BlobContainerItem::getName)).contains(container);
        client.deleteBlobContainer(container);
        assertThat(containers.stream().map(BlobContainerItem::getName)).doesNotContain(container);
    }
}
