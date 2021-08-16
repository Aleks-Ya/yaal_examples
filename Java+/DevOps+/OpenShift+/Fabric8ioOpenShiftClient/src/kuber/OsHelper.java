package kuber;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.client.dsl.PodResource;
import io.fabric8.openshift.client.OpenShiftClient;

public class OsHelper {
    private final OpenShiftClient client;

    public OsHelper(OpenShiftClient client) {
        this.client = client;
    }

    public Pod podByName(String podName) {
        return client.pods().list().getItems().stream()
                .filter(map -> podName.equals(map.getMetadata().getName()))
                .findFirst()
                .orElseThrow();
    }

    public ConfigMap cmByName(String cmName) {
        return client.configMaps().list().getItems().stream()
                .filter(map -> cmName.equals(map.getMetadata().getName()))
                .findFirst()
                .orElseThrow();
    }

    public Secret secretByName(String secretName) {
        return client.secrets().list().getItems().stream()
                .filter(map -> secretName.equals(map.getMetadata().getName()))
                .findFirst()
                .orElseThrow();
    }

    public String podLogs(String podName) {
        return podResourceByName(podName).getLog();
    }

    private PodResource<Pod> podResourceByName(String podName) {
        var pod = podByName(podName);
        return client.pods().inNamespace(pod.getMetadata().getNamespace()).withName(podName);
    }

    public void waitUntilRunning(String podName) {
        var podResource = podResourceByName(podName);
        var watcher = new RunningPodWatcher(podName);
        podResource.watch(watcher);
        watcher.waitUntilIsRunning();
    }
}
