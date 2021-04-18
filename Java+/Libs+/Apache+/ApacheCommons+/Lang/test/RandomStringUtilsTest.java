import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

public class RandomStringUtilsTest {

    @Test
    public void random() {
        String random = RandomStringUtils.random(20);
        System.out.println(random);
    }

    @Test
    public void random2() {
        String random = RandomStringUtils.random(20, true, true);
        System.out.println(random);
    }

}
