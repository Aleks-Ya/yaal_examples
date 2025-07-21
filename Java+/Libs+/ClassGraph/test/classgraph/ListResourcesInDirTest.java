package classgraph;

import io.github.classgraph.ClassGraph;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListResourcesInDirTest {

    @Test
    void listResourcesInDir() {
        try (var scanResult = new ClassGraph()
                .acceptPackages("classgraph")
                .scan()) {
            var txtResources = scanResult.getResourcesWithExtension("txt");
            assertThat(txtResources).hasSize(2);
        }
    }

}