import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class Main {
    @Test
    public void testName() {
        PodamFactory factory = new PodamFactoryImpl();
        Country myPojo = factory.manufacturePojo(Country.class);
        System.out.println(myPojo);
    }
}
