import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Settings {
    @XmlAttribute
    private String id;

    @XmlAttribute
    private int port;

    @XmlElement
    private Mask mask;

    @XmlElement
    @XmlElementWrapper(name = "lamps")
    private List<Lamp> lamp;

    /**
     * Содержимое тега book парсится в виде строки (в отличие от Lamp).
     */
    @XmlElementWrapper(name = "books")
    @XmlElement(type = String.class)
    private List<String> book;


    @Override
    public String toString() {
        return String.format("Settings[id='%s' port='%d' mask='%s' lamp='%s' book='%s']", id, port, mask, lamp, book);
    }
}
