package joplin;

import java.time.LocalDate;

public record Date(NoteEntity entity, String element, LocalDate localDate) {
}
