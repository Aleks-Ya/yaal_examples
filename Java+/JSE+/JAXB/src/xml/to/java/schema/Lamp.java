package xml.to.java.schema;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
class Lamp {
    @XmlValue
    private String value;

    String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Lamp[value='%s']", value);
    }
}