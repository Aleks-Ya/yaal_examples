import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws JAXBException {
        InputStream is = Main.class.getResourceAsStream("data.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Settings settings = (Settings) jaxbUnmarshaller.unmarshal(is);
        System.out.println(settings);
    }
}