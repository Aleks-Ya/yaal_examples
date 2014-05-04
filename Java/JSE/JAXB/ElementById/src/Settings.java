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

    public String getId() {
        return id;
    }

    public int getPort() {
        return port;
    }

    public Mask getMask() {
        return mask;
    }

    public List<Lamp> getLamp() {
        return lamp;
    }

    @Override
    public String toString() {
        return String.format("Settings[id='%s' port='%d' mask='%s' lamp='%s']", getId(), getPort(), getMask(), getLamp());
    }
}
