package xml.to.java.lamp;

import javax.xml.bind.annotation.XmlElement;

class Environment {
    @XmlElement
    private String root;
    @XmlElement
    private String path;

    @Override
    public String toString() {
        return String.format("Environment[root='%s' path='%s']", root, path);
    }
}
