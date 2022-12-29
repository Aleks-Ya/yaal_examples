package joplin.common.date;

import joplin.common.note.NoteId;

import java.time.LocalDate;

public record Date(NoteId noteId, String element, LocalDate localDate) {
}
