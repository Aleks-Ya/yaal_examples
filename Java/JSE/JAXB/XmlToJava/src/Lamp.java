import javax.xml.bind.annotation.XmlValue;

public class Lamp {
    @XmlValue
    private String value;

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Lamp[value='%s']", getValue());
    }
}
