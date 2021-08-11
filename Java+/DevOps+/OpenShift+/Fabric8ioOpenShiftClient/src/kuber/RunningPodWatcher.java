package kuber;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;

public class RunningPodWatcher implements Watcher<Pod> {
    private final String podName;
    private boolean isRunning = false;
    private WatcherException exception;

    public RunningPodWatcher(String podName) {
        this.podName = podName;
    }

    @Override
    public void eventReceived(Action action, Pod resource) {
        if (podName.equals(resource.getMetadata().getName())) {
            isRunning = "Running".equals(resource.getStatus().getPhase());
        }
    }

    @Override
    public void onClose(WatcherException cause) {
        exception = cause;
    }

    public void waitUntilIsRunning() {
        while (!isRunning) {
            try {
                Thread.sleep(500);
                if (exception != null) {
                    throw exception;
                }
            } catch (InterruptedException | WatcherException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
