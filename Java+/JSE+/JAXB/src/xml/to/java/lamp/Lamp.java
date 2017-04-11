package xml.to.java.lamp;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

class Lamp {
    @XmlValue
    private String value;

    /**
     * Перечислимое.
     */
    @XmlAttribute
    private LampType type;

    @Override
    public String toString() {
        return String.format("Lamp[value='%s' type=%s]", value, type);
    }
}