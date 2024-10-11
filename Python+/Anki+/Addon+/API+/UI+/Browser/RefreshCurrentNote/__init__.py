from datetime import datetime

from anki.notes import Note, NoteId
from aqt.browser import Browser

from ._common.disable import enabled
from ._common import menu


def __on_event_editor(browser: Browser):
    note: Note = browser.editor.note
    note['English'] = f"{note['English']} {datetime.now().time()}"
    browser.col.update_note(note)
    browser.editor.loadNoteKeepingFocus()


def __on_event_collection(browser: Browser):
    note_id: NoteId = browser.editor.note.id
    note: Note = browser.col.get_note(note_id)
    note['English'] = f"{note['English']} {datetime.now().time()}"
    browser.col.update_note(note)
    browser.editor.set_note(note)


if enabled(True):
    menu.add_browser_menu("Update note in Editor and refresh Editor", __on_event_editor)
    menu.add_browser_menu("Update note in Collection and refresh Editor", __on_event_collection)
