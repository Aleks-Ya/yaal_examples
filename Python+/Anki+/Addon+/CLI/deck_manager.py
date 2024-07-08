import tempfile
import unittest
from typing import Optional

from anki.collection import Collection, OpChangesWithId
from anki.decks import DeckManager, DeckId, Deck, DeckDict


class TestDeckManager(unittest.TestCase):
    default_deck_id: int = 1
    default_deck_name: str = 'Default'
    default_deck: DeckDict = {'id': default_deck_id, 'mod': 0, 'name': default_deck_name, 'usn': 0, 'lrnToday': [0, 0],
                              'revToday': [0, 0], 'newToday': [0, 0], 'timeToday': [0, 0],
                              'collapsed': True, 'browserCollapsed': True, 'desc': '', 'dyn': 0,
                              'conf': 1, 'extendNew': 0, 'extendRev': 0, 'reviewLimit': None,
                              'newLimit': None, 'reviewLimitToday': None, 'newLimitToday': None}

    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])
        self.deck_manager: DeckManager = self.col.decks

    def test_all(self):
        all_decks: list[DeckDict] = self.deck_manager.all()
        self.assertListEqual([self.default_deck], all_decks)

    def test_current(self):
        deck: DeckDict = self.deck_manager.current()
        self.assertEqual(self.default_deck, deck)

    def test_get_current_id(self):
        deck_id: DeckId = self.deck_manager.get_current_id()
        deck: Optional[DeckDict] = self.deck_manager.get(deck_id)
        self.assertEqual(self.default_deck, deck)

    def test_active(self):
        active: list[DeckId] = self.deck_manager.active()
        self.assertEqual([self.default_deck_id], active)

    def test_name(self):
        deck_name: str = self.deck_manager.name(DeckId(1))
        self.assertEqual(self.default_deck_name, deck_name)

    def test_by_name(self):
        deck: Optional[DeckDict] = self.deck_manager.by_name(self.default_deck_name)
        self.assertEqual(self.default_deck, deck)

    def test_add_deck(self):
        deck_name: str = "Deck1"
        deck: Deck = self.deck_manager.new_deck()
        deck.name = deck_name
        op: OpChangesWithId = self.deck_manager.add_deck(deck)
        self.assertTrue(op.changes.deck)
        act_deck: Optional[Deck] = self.deck_manager.by_name(deck_name)
        self.assertEqual(act_deck['name'], deck_name)

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()
