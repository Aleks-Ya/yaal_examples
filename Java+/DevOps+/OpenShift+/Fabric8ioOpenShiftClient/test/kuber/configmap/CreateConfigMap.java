package kuber.configmap;

import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static kuber.ClientFactory.devClient;
import static kuber.ClientFactory.devHelper;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateConfigMap {

    @Test
    void createConfigMap() {
        var dataName = "name";
        var cmName = "cm-" + UUID.randomUUID();
        var cm = new ConfigMapBuilder()
                .withNewMetadata().withName(cmName).endMetadata()
                .withData(Map.of(dataName, "John"))
                .build();
        var client = devClient();
        client.configMaps().create(cm);

        var actCm = devHelper().cmByName(cmName);
        assertThat(actCm.getData().get(dataName), equalTo("John"));

        assertTrue(client.configMaps().delete(actCm));
    }

}
