package classgraph;

import io.github.classgraph.ClassGraph;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListResourcesInDirTest {

    @Test
    void listResourcesInDir_acceptPackages() {
        try (var scanResult = new ClassGraph()
                .acceptPackages("classgraph")
                .scan()) {
            var txtResources = scanResult.getResourcesWithExtension("txt");
            assertThat(txtResources).hasSize(2);
        }
    }

    @Test
    void listResourcesInDir_acceptPaths() {
        try (var scanResult = new ClassGraph()
                .acceptPaths("classgraph")
                .scan()) {
            var txtResources = scanResult.getResourcesWithExtension("txt");
            assertThat(txtResources).hasSize(2);
        }
    }


}