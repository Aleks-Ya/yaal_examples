package lang;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

class RandomStringUtilsTest {

    @Test
    void random() {
        var random = RandomStringUtils.random(20);
        System.out.println(random);
    }

    @Test
    void random2() {
        var random = RandomStringUtils.random(20, true, true);
        System.out.println(random);
    }

}
