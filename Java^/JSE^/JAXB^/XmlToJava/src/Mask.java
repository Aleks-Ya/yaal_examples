import javax.xml.bind.annotation.XmlValue;

class Mask {
    private String value;

    public String getValue() {
        return value;
    }

    @XmlValue
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Mask[value='%s']", getValue());
    }
}
