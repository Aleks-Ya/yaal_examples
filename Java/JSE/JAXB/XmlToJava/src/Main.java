import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Main {
    public static void main(String[] args) throws JAXBException {
        File file = new File(Main.class.getResource("data.xml").getFile());
        JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Settings settings = (Settings) jaxbUnmarshaller.unmarshal(file);
        System.out.println(settings);
    }
}