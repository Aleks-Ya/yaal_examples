# Docs: https://addon-docs.ankiweb.net/the-anki-module.html?highlight=databa#the-database

from anki.cards import CardId, Card
from anki.collection import Collection
from anki.decks import DeckId
from anki.models import NoteType
from anki.notes import Note
from anki.scheduler.v3 import CardAnswer


def test_rev_log_size(col: Collection):
    size: int = col.db.scalar("SELECT SUM(pgsize) FROM dbstat WHERE name = 'revlog'")
    print(f"Size in bytes: {size}")


def test_rev_log_count(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note: Note = col.new_note(basic_note_type)
    col.add_note(note, deck_id)
    card_id: CardId = col.card_ids_of_note(note.id)[0]
    card: Card = col.get_card(card_id)
    card.start_timer()
    col.sched.answerCard(card, CardAnswer.GOOD)
    count: int = col.db.scalar("SELECT count(id) FROM revlog")
    assert count == 1
