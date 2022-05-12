package xml.xpath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import static org.assertj.core.api.Assertions.assertThat;

class SelectorTest {
    private static XPath xPath;
    private static Document doc;

    @BeforeAll
    static void initXPath() {
        var factory = new XPathResrouceFactory("xml/xpath/SelectorTest.xml");
        xPath = factory.getXPath();
        doc = factory.getDoc();
    }

    @Test
    void getNodeByName() throws XPathExpressionException {
        var node = (Node) xPath.evaluate("/A/B/C", doc, XPathConstants.NODE);
        assertThat(node.getNodeName()).isEqualTo("C");
    }

    @Test
    void getNodeByNameAsList() throws XPathExpressionException {
        var nodeList = (NodeList) xPath.evaluate("/A/B/C", doc, XPathConstants.NODESET);
        assertThat(nodeList.getLength()).isEqualTo(1);
    }

    @Test
    void getNodeListByName() throws XPathExpressionException {
        var nodeList = (NodeList) xPath.evaluate("/A/D/E", doc, XPathConstants.NODESET);
        assertThat(nodeList.getLength()).isEqualTo(2);
    }

    @Test
    void getNodeListByNameAnyDepth() throws XPathExpressionException {
        var nodeList = (NodeList) xPath.evaluate("//E", doc, XPathConstants.NODESET);
        assertThat(nodeList.getLength()).isEqualTo(2);
    }

    @Test
    void getNodeByClassEquals() throws XPathExpressionException {
        var node = (Node) xPath.evaluate("/A/F/G[@class='c1']", doc, XPathConstants.NODE);
        assertThat(node.getNodeName()).isEqualTo("G");
        assertThat(node.getAttributes().getNamedItem("class").getNodeValue()).isEqualTo("c1");
    }

    @Test
    void getNodeByClassContains() throws XPathExpressionException {
        var node = (Node) xPath.evaluate("/A/F/G[contains(@class, 'element')]", doc, XPathConstants.NODE);
        assertThat(node.getNodeName()).isEqualTo("G");
        assertThat(node.getAttributes().getNamedItem("class").getNodeValue()).isEqualTo("main_element_2");
    }

    @Test
    void getNodeByClassStartsWith() throws XPathExpressionException {
        var node = (Node) xPath.evaluate("/A/F/G[starts-with(@class, 'main_element')]", doc, XPathConstants.NODE);
        assertThat(node.getNodeName()).isEqualTo("G");
        assertThat(node.getAttributes().getNamedItem("class").getNodeValue()).isEqualTo("main_element_2");
    }
}
