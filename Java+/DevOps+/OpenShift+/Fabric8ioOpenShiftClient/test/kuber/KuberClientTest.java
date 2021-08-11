package kuber;

import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.openshift.api.model.Project;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import org.junit.jupiter.api.Test;

class KuberClientTest {

    @Test
    void listProjects() {
        var config = new ConfigBuilder()
                .withMasterUrl("https://api.crc.testing:6443")
                .withUsername("developer")
                .withPassword("developer")
                .build();
        OpenShiftClient client = new DefaultOpenShiftClient(config);
        var projects = client.projects().list().getItems();
        for (Project project : projects) {
            System.out.println(project.getMetadata().getName());
        }
    }

}
