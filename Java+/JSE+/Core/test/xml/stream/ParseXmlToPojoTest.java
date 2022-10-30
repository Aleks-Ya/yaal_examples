package xml.stream;

import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class ParseXmlToPojoTest {

    @Test
    void parse() throws XMLStreamException {
        var xmlInputFactory = XMLInputFactory.newInstance();
        var is = ResourceUtil.resourceToInputStream("xml/stream/ParseXmlToPojoTest.xml");
        var reader = xmlInputFactory.createXMLEventReader(is);

        var books = new ArrayList<Book>();
        String title = null;
        List<String> authors = new ArrayList<>();
        String publisher = null;
        String isbn = null;
        BigDecimal price = null;
        Integer year = null;
        while (reader.hasNext()) {
            var nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                var startElement = nextEvent.asStartElement();
                if (startElement.getName().getLocalPart().equals("book")) {
                    title = null;
                    authors = new ArrayList<>();
                    publisher = null;
                    isbn = null;
                    price = null;
                    year = Integer.parseInt(startElement.getAttributeByName(new QName("year")).getValue());
                }
                if (startElement.getName().getLocalPart().equals("title")) {
                    nextEvent = reader.nextEvent();
                    title = nextEvent.asCharacters().getData();
                }
                if (startElement.getName().getLocalPart().equals("author")) {
                    nextEvent = reader.nextEvent();
                    authors.add(nextEvent.asCharacters().getData());
                }
                if (startElement.getName().getLocalPart().equals("publisher")) {
                    nextEvent = reader.nextEvent();
                    publisher = nextEvent.asCharacters().getData();
                }
                if (startElement.getName().getLocalPart().equals("isbn")) {
                    nextEvent = reader.nextEvent();
                    isbn = nextEvent.asCharacters().getData();
                }
                if (startElement.getName().getLocalPart().equals("price")) {
                    nextEvent = reader.nextEvent();
                    price = new BigDecimal(nextEvent.asCharacters().getData());
                }
            }
            if (nextEvent.isEndElement()) {
                var endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("book")) {
                    books.add(new Book(title, authors, publisher, isbn, price, year));

                }
            }
        }
        assertThat(books).containsExactly(
                new Book("Snow Crash", List.of("Neal Stephenson"), "Spectra", "0553380958",
                        new BigDecimal("14.95"), 2000),
                new Book("Burning Tower", List.of("Larry Niven", "Jerry Pournelle"), "Pocket", "0743416910",
                        new BigDecimal("5.99"), 2005),
                new Book("Zodiac", List.of("Neal Stephenson"), "Spectra", "0553573862",
                        new BigDecimal("7.50"), 1995)
        );
    }

    static class Book {
        private final String title;
        private final List<String> authors;
        private final String publisher;
        private final String isbn;
        private final BigDecimal price;
        private final Integer year;

        Book(String title, List<String> authors, String publisher, String isbn, BigDecimal price, Integer year) {
            this.title = title;
            this.authors = authors;
            this.publisher = publisher;
            this.isbn = isbn;
            this.price = price;
            this.year = year;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Book book = (Book) o;
            return Objects.equals(title, book.title) && Objects.equals(authors, book.authors) && Objects.equals(publisher, book.publisher) && Objects.equals(isbn, book.isbn) && Objects.equals(price, book.price) && Objects.equals(year, book.year);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, authors, publisher, isbn, price, year);
        }
    }
}
