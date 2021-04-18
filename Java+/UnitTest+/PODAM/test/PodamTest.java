import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class PodamTest {
    @Test
    public void test() {
        PodamFactory factory = new PodamFactoryImpl();
        Country myPojo = factory.manufacturePojo(Country.class);
        System.out.println(myPojo);
    }
}
