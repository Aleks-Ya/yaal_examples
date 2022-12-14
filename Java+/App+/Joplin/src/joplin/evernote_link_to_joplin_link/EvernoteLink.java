package joplin.evernote_link_to_joplin_link;

import joplin.NoteEntity;

record EvernoteLink(NoteEntity noteEntity, String matchedText, String title) {
}
