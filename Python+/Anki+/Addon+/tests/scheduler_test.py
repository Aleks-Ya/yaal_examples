from anki.collection import Collection
from anki.cards import Card
from anki.collection_pb2 import OpChanges
from anki.consts import BUTTON_THREE
from anki.notes import Note


def test_answer_card(col: Collection):
    note: Note = col.newNote()
    col.addNote(note)
    card_id: int = col.card_ids_of_note(note.id)[0]
    card: Card = col.get_card(card_id)
    card.start_timer()
    op_changes: OpChanges = col.sched.answerCard(card, BUTTON_THREE)
    assert op_changes.card
    assert op_changes.deck
    assert op_changes.mtime
    assert op_changes.browser_table
    assert op_changes.browser_sidebar
    assert op_changes.study_queues
