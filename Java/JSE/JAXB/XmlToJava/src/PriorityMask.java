import javax.xml.bind.annotation.XmlAttribute;

public class PriorityMask extends Mask {
    @XmlAttribute
    private int priority;

    @Override
    public String toString() {
        return String.format("Mask[value='%s' priority='%d']", getValue(), priority);
    }
}
