package anki.missedwords;

import anki.connect.AnkiConnect;
import anki.connect.AnkiNote;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static anki.missedwords.Constants.ENGLISH_FIELD;
import static java.util.stream.Collectors.joining;

class EnglishWordsWithHtmlTagsTest {
    @Test
    void printEnglishWordsContainsHtmlTags() {
        var ankiConnect = new AnkiConnect();
        var wordsWithHtmlTags = findNotesWithHtmlTags(ankiConnect);
        System.out.printf("Found %d notes: %s\n", wordsWithHtmlTags.size(), wordsWithHtmlTags.stream()
                .map(note -> note.fields().get(ENGLISH_FIELD))
                .filter(Objects::nonNull)
                .toList());
    }

    @Test
    void cleanupEnglishWordsContainsHtmlTags() {
        var ankiConnect = new AnkiConnect();
        var wordsWithHtmlTags = findNotesWithHtmlTags(ankiConnect);

        for (var i = 0; i < wordsWithHtmlTags.size(); i++) {
            var note = wordsWithHtmlTags.get(i);
            var oldValue = note.fields().get(ENGLISH_FIELD).value();
            var newValue = oldValue.replace("<div>", "").replace("</div>", "");
            ankiConnect.updateNoteFields(note.noteId(), ENGLISH_FIELD, newValue);
            if (i % 50 == 0) {
                System.out.printf("Updated %d from %d\n", i, wordsWithHtmlTags.size());
            }
        }
    }

    private static List<AnkiNote> findNotesWithHtmlTags(AnkiConnect ankiConnect) {
        var noteIds = ankiConnect.findNotes();
        var notes = ankiConnect.notesInfo(noteIds);
        var wordsWithHtmlTags = notes.stream()
                .filter(note -> {
                    var field = note.fields().get(ENGLISH_FIELD);
                    return field != null && field.value().contains("<");
                })
                .toList();
        System.out.println("Words count: " + wordsWithHtmlTags.size());
        System.out.println(wordsWithHtmlTags.stream()
                .map(note -> note.fields().get(ENGLISH_FIELD).value())
                .collect(joining("\n")));
        return wordsWithHtmlTags;
    }
}
