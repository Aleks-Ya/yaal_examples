package joplin;

import java.time.LocalDate;

public record Date(NoteId noteId, String element, LocalDate localDate) {
}
