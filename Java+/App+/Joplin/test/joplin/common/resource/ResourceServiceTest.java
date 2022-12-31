package joplin.common.resource;

import joplin.common.link.Link;
import org.junit.jupiter.api.Test;

import java.io.File;

import static joplin.Utils.getJoplinDir;
import static org.assertj.core.api.Assertions.assertThat;

class ResourceServiceTest {
    @Test
    void getDecryptedJoplinResource() {
        var joplinDir = getJoplinDir();
        var resourceService = new ResourceService(joplinDir);
        var link = new Link("[Link1](:/00b0cb57260c885342e9a5cda5efd5eb)",
                "Link1", ":/00b0cb57260c885342e9a5cda5efd5eb", null);
        var resourceOpt = resourceService.getDecryptedJoplinResource(link);
        assertThat(resourceOpt).hasValue(new Resource(new ResourceId("00b0cb57260c885342e9a5cda5efd5eb"),
                new File(joplinDir, "/resources/00b0cb57260c885342e9a5cda5efd5eb.txt")));
    }
}