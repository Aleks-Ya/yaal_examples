package kuber.project;

import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import io.fabric8.openshift.api.model.Project;
import io.fabric8.openshift.api.model.ProjectBuilder;
import kuber.ClientFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WatchProject {

    @Test
    void watchProject() throws InterruptedException {
        var projectName = "my-project-" + UUID.randomUUID();
        var project = new ProjectBuilder()
                .withNewSpec().endSpec()
                .withNewMetadata().withName(projectName).endMetadata()
                .build();
        var client = ClientFactory.adminClient();
        var createdProject = client.projects().create(project);
        var watcher = new ProjectCreatedWatcher();
        client.projects().watch(watcher);
        watcher.waitUntilIsCreated();
        assertThat(createdProject.getStatus().getPhase(), equalTo("Active"));

        Thread.sleep(1000);

        var actProject = client.projects().list().getItems().stream()
                .filter(p -> projectName.equals(p.getMetadata().getName()))
                .findFirst()
                .orElseThrow();
        assertThat(actProject.getMetadata().getUid(), equalTo(createdProject.getMetadata().getUid()));

        assertTrue(client.projects().delete(actProject));
    }

    private static class ProjectCreatedWatcher implements Watcher<Project> {
        private boolean isCreated = false;

        @Override
        public void eventReceived(Action action, Project resource) {
            System.out.println("Action: " + action);
            System.out.println("Phase: " + resource.getStatus().getPhase());
            isCreated = action == Action.ADDED;
        }

        @Override
        public void onClose(WatcherException cause) {
            System.out.println("Watcher exception: " + cause);
        }

        public void waitUntilIsCreated() {
            while (!isCreated) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
