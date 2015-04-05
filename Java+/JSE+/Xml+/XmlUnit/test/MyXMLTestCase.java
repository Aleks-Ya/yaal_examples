import org.custommonkey.xmlunit.XMLTestCase;

/**
 * Кейс из User Guide.
 */
public class MyXMLTestCase extends XMLTestCase {
    public MyXMLTestCase(String name) {
        super(name);
    }

    public void testForEquality() throws Exception {
        String myControlXML = "<msg><uuid>0x00435A8C</uuid></msg>";
        String myTestXML = "<msg><localId>2376</localId></msg>";
        assertXMLNotEqual("Comparing test xml to control xml",  myControlXML, myTestXML);
    }
}