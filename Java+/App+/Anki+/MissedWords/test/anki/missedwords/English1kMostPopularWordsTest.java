package anki.missedwords;

import anki.connect.AnkiConnect;
import anki.connect.AnkiField;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static anki.missedwords.Constants.ENGLISH_1K_TAG;
import static anki.missedwords.Constants.ENGLISH_FIELD;
import static java.nio.file.Files.readAllLines;
import static util.ResourceUtil.resourceToPath;

class English1kMostPopularWordsTest {
    @Test
    void printMissedUnknownWords() throws IOException {
        var words1k = readAllLines(resourceToPath(getClass(), "english_1000_most_popular.txt"));
        var knownWords = readAllLines(resourceToPath(getClass(), "known_words_from_1000.txt"));

        var ankiConnect = new AnkiConnect();
        var noteIds = ankiConnect.findNotes();
        var notes = ankiConnect.notesInfo(noteIds);
        var ankiEnglishWords = notes.stream()
                .map(note -> note.fields().get(ENGLISH_FIELD))
                .filter(Objects::nonNull)
                .map(AnkiField::value)
                .toList();
        var missedWords = words1k.stream()
                .filter(word -> !knownWords.contains(word))
                .filter(word -> !ankiEnglishWords.contains(word))
                .toList();
        System.out.println("Missed words count: " + missedWords.size());
        System.out.println(String.join("\n", missedWords));
    }

    @Test
    void addTagTo1kWords() throws IOException {
        var words1k = readAllLines(resourceToPath(getClass(), "english_1000_most_popular.txt"));

        var ankiConnect = new AnkiConnect();
        var noteIds = ankiConnect.findNotes();
        var notes = ankiConnect.notesInfo(noteIds);
        var english1kWords = notes.stream()
                .filter(note -> {
                    var field = note.fields().get(ENGLISH_FIELD);
                    return field != null && words1k.contains(field.value());
                })
                .toList();
        System.out.println("Words count: " + english1kWords.size());

        var english1kWordsWithoutTag = english1kWords.stream()
                .filter(note -> !note.tags().contains(ENGLISH_1K_TAG))
                .toList();
        System.out.println("Words count without tag: " + english1kWordsWithoutTag.size());

        System.out.println(String.join("\n", english1kWordsWithoutTag.stream()
                .map(note -> note.fields().get(ENGLISH_FIELD))
                .filter(Objects::nonNull)
                .map(AnkiField::value)
                .toList()));

        english1kWordsWithoutTag.forEach(note -> ankiConnect.addTags(List.of(note.noteId()), List.of(ENGLISH_1K_TAG)));
        System.out.println("Finished");
    }

}
