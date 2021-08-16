package kuber.project;

import io.fabric8.openshift.api.model.ProjectBuilder;
import kuber.ClientFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectTest
{
    @Test
    void listProjects() {
        var client = ClientFactory.devClient();
        var projects = client.projects().list().getItems();
        for (var project : projects) {
            System.out.println(project.getMetadata().getName());
        }
    }

    @Test
    void createProject() throws InterruptedException {
        var projectName = "my-project-" + UUID.randomUUID();
        var project = new ProjectBuilder()
                .withNewSpec().endSpec()
                .withNewMetadata().withName(projectName).endMetadata()
                .build();
        var client = ClientFactory.adminClient();
        var createdProject = client.projects().create(project);
        assertThat(createdProject.getStatus().getPhase(), equalTo("Active"));

        Thread.sleep(1000);

        var actProject = client.projects().list().getItems().stream()
                .filter(p -> projectName.equals(p.getMetadata().getName()))
                .findFirst()
                .orElseThrow();
        assertThat(actProject.getMetadata().getUid(), equalTo(createdProject.getMetadata().getUid()));

        assertTrue(client.projects().delete(actProject));
    }
}
