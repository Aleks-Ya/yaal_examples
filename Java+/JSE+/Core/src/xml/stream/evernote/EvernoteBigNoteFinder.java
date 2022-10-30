package xml.stream.evernote;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Find N biggest notes in Evernote export file (*.ENEX).
 */
public class EvernoteBigNoteFinder {
    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        var enex = "/home/aleks/Public/IT_2022-10-30.enex";
        var topNumber = 20;
        var notes = findAllNotest(enex);
        var topNotes = findTopNotes(topNumber, notes);
        printTopNotes(topNotes);
    }

    private static ArrayList<Note> findAllNotest(String enex) throws FileNotFoundException, XMLStreamException {
        var xmlInputFactory = XMLInputFactory.newInstance();
        var is = new FileInputStream(enex);
        var reader = xmlInputFactory.createXMLEventReader(is);

        var notes = new ArrayList<Note>();
        String title = null;
        var contentLength = 0;
        List<Integer> resourceLengths = new ArrayList<>();
        while (reader.hasNext()) {
            var nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                var startElement = nextEvent.asStartElement();
                if (startElement.getName().getLocalPart().equals("note")) {
                    title = null;
                    contentLength = 0;
                    resourceLengths = new ArrayList<>();
                }
                if (startElement.getName().getLocalPart().equals("title")) {
                    title = reader.getElementText();
                }
                if (startElement.getName().getLocalPart().equals("content")) {
                    if (contentLength > 0) {
                        throw new IllegalStateException("Several contents in one note");
                    }
                    contentLength = reader.getElementText().length();
                }
                if (startElement.getName().getLocalPart().equals("data")) {
                    resourceLengths.add(reader.getElementText().length());
                }
            }
            if (nextEvent.isEndElement()) {
                var endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("note")) {
                    notes.add(new Note(title, contentLength, resourceLengths));

                }
            }
        }
        System.out.println("Notes count: " + notes.size());
        return notes;
    }

    private static List<Note> findTopNotes(int topNumber, ArrayList<Note> notes) {
        return notes.stream()
                .sorted((n1, n2) -> n2.totalSize().compareTo(n1.totalSize()))
                .limit(topNumber)
                .toList();
    }

    private static void printTopNotes(List<Note> topNotes) {
        var topNotesStr = new StringBuilder();
        for (var i = 0; i < topNotes.size(); i++) {
            var note = topNotes.get(i);
            topNotesStr.append(format("%2d. '%s' - %,d bytes\n", i + 1, note.title, note.totalSize()));
        }
        System.out.println("Top notes:\n" + topNotesStr);
    }

    static class Note {
        private final String title;
        private final Integer contentLength;
        private final List<Integer> resourceLengths;

        Note(String title, Integer contentLength, List<Integer> resourceLengths) {
            this.title = title;
            this.contentLength = contentLength;
            this.resourceLengths = resourceLengths;
        }

        public Long totalSize() {
            return contentLength + resourceLengths.stream().mapToLong(Integer::longValue).sum();
        }

        @Override
        public String toString() {
            return "Note{" +
                    "title='" + title + '\'' +
                    ", totalSize=" + totalSize() +
                    '}';
        }
    }
}
