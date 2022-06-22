package lang.reflection.packages;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetJavaPackageTest {
    @Test
    void getPackage() {
        var packageName = "lang.reflection.packages";
        var pack = getClass().getClassLoader().getDefinedPackage(packageName);
        assertThat(pack.getName()).isEqualTo(packageName);
    }
}
