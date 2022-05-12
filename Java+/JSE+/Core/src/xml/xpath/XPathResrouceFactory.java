package xml.xpath;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import util.ResourceUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import java.io.IOException;

public class XPathResrouceFactory {

    private final String resource;
    private XPath xPath;
    private Document doc;

    public XPathResrouceFactory(String resource) {
        this.resource = resource;
    }

    private void init() {
        try {
            var file = ResourceUtil.resourceToFile(resource);
            var factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(file);
            var xPathFactory = javax.xml.xpath.XPathFactory.newInstance();
            xPath = xPathFactory.newXPath();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public XPath getXPath() {
        if (xPath == null) {
            init();
        }
        return xPath;
    }

    public Document getDoc() {
        if (doc == null) {
            init();
        }
        return doc;
    }
}
