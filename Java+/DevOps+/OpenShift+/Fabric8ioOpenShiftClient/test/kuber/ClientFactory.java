package kuber;

import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.openshift.api.model.Project;
import io.fabric8.openshift.api.model.ProjectBuilder;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

import java.util.UUID;

public class ClientFactory {

    public static OpenShiftClient createDeveloperClient() {
        return createClient("developer", "developer");
    }

    public static OpenShiftClient createAdminClient() {
        return createClient("kubeadmin", "9QqQz-AbIbM-dCoqA-NPL4t");
    }

    private static OpenShiftClient createClient(String username, String password) {
        var config = new ConfigBuilder()
                .withMasterUrl("https://api.crc.testing:6443")
                .withUsername(username)
                .withPassword(password)
                .build();
        return new DefaultOpenShiftClient(config);
    }
}
