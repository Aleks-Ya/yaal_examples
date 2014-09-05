import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
class Settings {
    @XmlAttribute
    private String id;

    @XmlAttribute
    private int port;

    @XmlElement
    private Mask mask;

    @XmlElement(name = "priorMask")
    private PriorityMask priorityMask;

    @XmlElement
    @XmlElementWrapper(name = "lamps")
    private List<Lamp> lamp;

    /**
     * Содержимое тега book парсится в виде строки (в отличие от Lamp).
     */
    @XmlElementWrapper(name = "books")
    @XmlElement(type = String.class)
    private List<String> book;

    @XmlElement
    private Environment environment;

    @Override
    public String toString() {
        return String.format("Settings[%n id='%s'%n port='%d'%n mask='%s'%n priority_mask='%s'%n lamp='%s'%n book='%s'%n environment='%s'%n]",
                id, port, mask, priorityMask, lamp, book, environment);
    }
}
