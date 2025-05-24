from typing import Any

from anki.collection import Collection
from anki.decks import DeckId
from anki.models import NoteType
from anki.notes import Note


def test_create_trigger_empty(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    col.db.execute("create trigger my_trigger after insert on notes begin select 1; end;")


def test_create_trigger_log_events(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    col.db.execute("create table note_events(event)")
    col.db.execute(
        "create trigger note_events after insert on notes begin insert into note_events values(NEW.id); end;")
    note1: Note = col.new_note(basic_note_type)
    note2: Note = col.new_note(basic_note_type)
    col.add_note(note1, deck_id)
    col.add_note(note2, deck_id)
    events: list[Any] = col.db.all("select event from note_events")
    assert events == [[note1.id], [note2.id]]
