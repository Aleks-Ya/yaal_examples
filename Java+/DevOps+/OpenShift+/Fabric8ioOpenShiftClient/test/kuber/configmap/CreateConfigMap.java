package kuber.configmap;

import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.openshift.api.model.Project;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import kuber.ClientFactory;
import org.junit.jupiter.api.Test;

class CreateConfigMap {

    @Test
    void listProjects() {
        var client = ClientFactory.createDeveloperClient();
    }

}
