import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Settings {
    @XmlAttribute
    private String id;

    @XmlAttribute
    private int port;

    @XmlElement
    private Mask mask;

    @XmlElement
    private Lamp lamp;

    public String getId() {
        return id;
    }

    public int getPort() {
        return port;
    }

    public Mask getMask() {
        return mask;
    }

    public Lamp getLamp() {
        return lamp;
    }

    @Override
    public String toString() {
        return String.format("Settings[id='%s' port='%d' mask='%s' lamp='%s']", getId(), getPort(), getMask(), getLamp());
    }
}
