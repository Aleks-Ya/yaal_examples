package anki.missedwords;

import anki.connect.AnkiConnect;
import anki.connect.AnkiField;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static anki.missedwords.Constants.CREATED_BY_API_TAG;
import static anki.missedwords.Constants.ENGLISH_5K_TAG;
import static anki.missedwords.Constants.ENGLISH_FIELD;
import static anki.missedwords.English5kMostPopularWordsHelper.partOfSpeachToTag;

class English5kMostPopularWordsTest {
    @Test
    void printMissedUnknownWords() throws IOException {
        var missedWords = English5kMostPopularWordsHelper.getMissingUnknownWords().stream()
                .map(Word::word)
                .toList();
        System.out.println("Missed words count: " + missedWords.size());
        System.out.println(String.join("\n", missedWords.stream().sorted().toList()));
    }

    @Test
    void addMissedUnknownWords() throws IOException {
        var missedWords = English5kMostPopularWordsHelper.getMissingUnknownWords();
        System.out.println("Missed words count: " + missedWords.size());

        var ankiConnect = new AnkiConnect();
        missedWords.forEach(word -> {
            var posTag = partOfSpeachToTag(word.partOfSpeech());
            var deckName = "En::English";
            var modelName = "En-word-or-sentence";
            var tags = List.of(posTag, ENGLISH_5K_TAG, CREATED_BY_API_TAG);
            var fields = Map.of(ENGLISH_FIELD, word.word());
            ankiConnect.addNote(deckName, modelName, tags, fields);
        });
    }

    @Test
    void addTagToAll5kWords() {
        var english5kWords = English5kMostPopularWordsHelper.readWordsFromResource()
                .stream().map(Word::word).toList();

        var ankiConnect = new AnkiConnect();
        var noteIds = ankiConnect.findNotes();
        var notes = ankiConnect.notesInfo(noteIds);
        var ankiEnglish5kWords = notes.stream()
                .filter(note -> {
                    var field = note.fields().get(ENGLISH_FIELD);
                    return field != null && english5kWords.contains(field.value());
                })
                .toList();
        System.out.println("Words count: " + ankiEnglish5kWords.size());

        var english5kWordsWithoutTag = ankiEnglish5kWords.stream()
                .filter(note -> !note.tags().contains(ENGLISH_5K_TAG))
                .toList();
        System.out.println("Words count without tag: " + english5kWordsWithoutTag.size());

        System.out.println(String.join("\n", english5kWordsWithoutTag.stream()
                .map(note -> note.fields().get(ENGLISH_FIELD))
                .filter(Objects::nonNull)
                .map(AnkiField::value)
                .toList()));

        english5kWordsWithoutTag.forEach(note -> ankiConnect.addTags(List.of(note.noteId()), List.of(ENGLISH_5K_TAG)));
        System.out.println("Finished");
    }

}
