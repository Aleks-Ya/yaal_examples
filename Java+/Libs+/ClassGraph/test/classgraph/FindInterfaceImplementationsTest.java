package classgraph;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FindInterfaceImplementationsTest {

    @Test
    void findImplementations() {
        try (var scanResult = new ClassGraph()
                .acceptPackages("classgraph")
                .scan()) {
            var implementations = scanResult.getClassesImplementing(Vehicle.class)
                    .stream().map(ClassInfo::getSimpleName).toList();
            assertThat(implementations).containsExactlyInAnyOrder(Car.class.getSimpleName(), Bus.class.getSimpleName());
        }
    }

}