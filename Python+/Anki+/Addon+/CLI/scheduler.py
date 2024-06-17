import tempfile
import unittest

from anki.collection import Collection
from anki.cards import Card
from anki.collection_pb2 import OpChanges
from anki.consts import BUTTON_THREE
from anki.notes import Note


class SchedulerTestCase(unittest.TestCase):
    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])

    def test_answer_card(self):
        note: Note = self.col.newNote()
        self.col.addNote(note)
        card_id: int = self.col.card_ids_of_note(note.id)[0]
        card: Card = self.col.get_card(card_id)
        card.start_timer()
        op_changes: OpChanges = self.col.sched.answerCard(card, BUTTON_THREE)
        self.assertTrue(op_changes.card)
        self.assertTrue(op_changes.deck)
        self.assertTrue(op_changes.mtime)
        self.assertTrue(op_changes.browser_table)
        self.assertTrue(op_changes.browser_sidebar)
        self.assertTrue(op_changes.study_queues)

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()
