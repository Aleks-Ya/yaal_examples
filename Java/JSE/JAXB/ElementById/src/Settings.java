import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Settings {
    private String id;
    private int port;
    private Mask mask;

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    @XmlAttribute
    public void setPort(int port) {
        this.port = port;
    }

    public Mask getMask() {
        return mask;
    }

    @XmlElement
    public void setMask(Mask mask) {
        this.mask = mask;
    }

    @Override
    public String toString() {
        return String.format("Settings[id='%s' port='%d' mask='%s']", getId(), getPort(), getMask());
    }
}
