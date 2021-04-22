package lang.reflection.packages;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class GetJavaPackage {
    @Test
    void getPackage() {
        var packageName = "lang.reflection.packages";
        var pack = getClass().getClassLoader().getDefinedPackage(packageName);
        assertThat(pack.getName(), equalTo(packageName));
    }
}
