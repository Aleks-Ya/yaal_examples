package xml.stream;

import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class XMLEventReaderTest {

    @Test
    void parse() throws XMLStreamException {
        var xmlInputFactory = XMLInputFactory.newInstance();
        var is = ResourceUtil.resourceToInputStream("xml/stream/XMLEventReaderTest.xml");
        var reader = xmlInputFactory.createXMLEventReader(is);

        var books = new ArrayList<String>();
        var book = new StringBuilder();
        while (reader.hasNext()) {
            var nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                var startElement = nextEvent.asStartElement();
                if (startElement.getName().getLocalPart().equals("book")) {
                    book.append("<book ");
                }
                if (startElement.getName().getLocalPart().equals("title")) {
                    var title = reader.getElementText();
                    book.append(title);
                }
            }
            if (nextEvent.isEndElement()) {
                var endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("book")) {
                    book.append(">");
                    books.add(book.toString());
                    book = new StringBuilder();
                }
            }
        }
        assertThat(books).containsExactly(
                "<book Snow Crash>", "<book Burning\nTower\nmulti-line>", "<book Zodiac\nMulti-Line>");
    }
}
