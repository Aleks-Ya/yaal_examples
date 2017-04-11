package xml.to.java.lamp;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
enum LampType {
    @XmlEnumValue("bright")
    BRIGHT,

    @XmlEnumValue("dark")
    DARK;
}
