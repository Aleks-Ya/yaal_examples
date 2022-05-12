package xml.xpath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import static org.assertj.core.api.Assertions.assertThat;

class CompileExpressionTest {
    private static XPath xPath;
    private static Document doc;

    @BeforeAll
    static void initXPath() {
        var factory = new XPathResrouceFactory("xml/xpath/SelectorTest.xml");
        xPath = factory.getXPath();
        doc = factory.getDoc();
    }

    @Test
    void explicitlyCompileExpression() throws XPathExpressionException {
        var expr = xPath.compile("/A/B/C");
        var node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        assertThat(node.getNodeName()).isEqualTo("C");
    }

    @Test
    void implicitlyCompileExpression() throws XPathExpressionException {
        var node = (Node) xPath.evaluate("/A/B/C", doc, XPathConstants.NODE);
        assertThat(node.getNodeName()).isEqualTo("C");
    }

}
