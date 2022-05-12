package xml.xpath;

import org.junit.jupiter.api.Test;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.ResourceUtil;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

/**
 * Source: https://howtodoinjava.com/java/xml/java-xpath-tutorial-example
 */
class XPathTest {

    @Test
    void xpath() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        var file = ResourceUtil.resourceToFile("xml/xpath/inventory.xml");

        var factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        var builder = factory.newDocumentBuilder();
        var doc = builder.parse(file);

        //Create XPath
        var xPathFactory = XPathFactory.newInstance();
        var xPath = xPathFactory.newXPath();

        System.out.println("n//1) Get book titles written after 2001");

        // 1) Get book titles written after 2001
        var expr = xPath.compile("//book[@year>2001]/title/text()");
        var result = expr.evaluate(doc, XPathConstants.NODESET);
        var nodes = (NodeList) result;
        for (var i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("n//2) Get book titles written before 2001");

        // 2) Get book titles written before 2001
        expr = xPath.compile("//book[@year<2001]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (var i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("n//3) Get book titles cheaper than 8 dollars");

        // 3) Get book titles cheaper than 8 dollars
        expr = xPath.compile("//book[price<8]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (var i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("n//4) Get book titles costlier than 8 dollars");

        // 4) Get book titles costlier than 8 dollars
        expr = xPath.compile("//book[price>8]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (var i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("n//5) Get book titles added in first node");

        // 5) Get book titles added in first node
        expr = xPath.compile("//book[1]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (var i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("n//6) Get book title added in last node");

        // 6) Get book title added in last node
        expr = xPath.compile("//book[last()]/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (var i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("n//7) Get all writers");

        // 7) Get all writers
        expr = xPath.compile("//book/author/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (var i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("n//8) Count all books titles ");

        // 8) Count all books titles
        expr = xPath.compile("count(//book/title)");
        result = expr.evaluate(doc, XPathConstants.NUMBER);
        var count = (Double) result;
        System.out.println(count.intValue());

        System.out.println("n//9) Get book titles with writer name start with Neal");

        // 9) Get book titles with writer name start with Neal
        expr = xPath.compile("//book[starts-with(author,'Neal')]");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (var i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i)
                    .getChildNodes()
                    .item(1)        //node <title> is on first index
                    .getTextContent());
        }

        System.out.println("n//10) Get book titles with writer name containing Niven");

        // 10) Get book titles with writer name containing Niven
        expr = xPath.compile("//book[contains(author,'Niven')]");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (var i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i)
                    .getChildNodes()
                    .item(1)        //node <title> is on first index
                    .getTextContent());
        }

        System.out.println("//11) Get book titles written by Neal Stephenson");

        // 11) Get book titles written by Neal Stephenson
        expr = xPath.compile("//book[author='Neal Stephenson']/title/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) result;
        for (var i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("n//12) Get count of book titles written by Neal Stephenson");

        // 12) Get count of book titles written by Neal Stephenson
        expr = xPath.compile("count(//book[author='Neal Stephenson'])");
        result = expr.evaluate(doc, XPathConstants.NUMBER);
        count = (Double) result;
        System.out.println(count.intValue());

        System.out.println("n//13) Reading comment node ");

        // 13) Reading comment node
        expr = xPath.compile("//inventory/comment()");
        result = expr.evaluate(doc, XPathConstants.STRING);
        var comment = (String) result;
        System.out.println(comment);
    }
}
