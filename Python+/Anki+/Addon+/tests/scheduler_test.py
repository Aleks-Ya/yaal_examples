from anki.collection import Collection
from anki.cards import Card, CardId
from anki.collection_pb2 import OpChanges
from anki.models import NoteType
from anki.notes import Note
from anki.scheduler.v3 import Scheduler, CardAnswer


def test_answer_card(col: Collection, basic_note_type: NoteType):
    note: Note = col.new_note(basic_note_type)
    col.addNote(note)
    card_id: CardId = col.card_ids_of_note(note.id)[0]
    card: Card = col.get_card(card_id)
    card.start_timer()
    scheduler: Scheduler = col.sched
    op_changes: OpChanges = scheduler.answerCard(card, CardAnswer.GOOD)
    assert op_changes.card
    assert op_changes.deck
    assert op_changes.mtime
    assert op_changes.browser_table
    assert op_changes.browser_sidebar
    assert op_changes.study_queues
