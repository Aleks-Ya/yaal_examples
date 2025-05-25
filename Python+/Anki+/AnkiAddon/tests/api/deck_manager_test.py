from typing import Optional

from anki.collection import OpChangesWithId
from anki.decks import DeckManager, DeckId, Deck, DeckDict

default_deck_id: int = 1
default_deck_name: str = 'Default'
default_deck: DeckDict = {'id': default_deck_id, 'mod': 0, 'name': default_deck_name, 'usn': 0, 'lrnToday': [0, 0],
                          'revToday': [0, 0], 'newToday': [0, 0], 'timeToday': [0, 0],
                          'collapsed': True, 'browserCollapsed': True, 'desc': '', 'dyn': 0,
                          'conf': 1, 'extendNew': 0, 'extendRev': 0, 'reviewLimit': None,
                          'newLimit': None, 'reviewLimitToday': None, 'newLimitToday': None}


def test_all(deck_manager: DeckManager):
    all_decks: list[DeckDict] = deck_manager.all()
    assert [default_deck] == all_decks


def test_current(deck_manager: DeckManager):
    deck: DeckDict = deck_manager.current()
    assert default_deck == deck


def test_get_current_id(deck_manager: DeckManager):
    deck_id: DeckId = deck_manager.get_current_id()
    deck: Optional[DeckDict] = deck_manager.get(deck_id)
    assert default_deck == deck


def test_active(deck_manager: DeckManager):
    active: list[DeckId] = deck_manager.active()
    assert [default_deck_id] == active


def test_name(deck_manager: DeckManager):
    deck_name: str = deck_manager.name(DeckId(1))
    assert default_deck_name == deck_name


def test_by_name(deck_manager: DeckManager):
    deck: Optional[DeckDict] = deck_manager.by_name(default_deck_name)
    assert default_deck == deck


def test_add_deck(deck_manager: DeckManager):
    deck_name: str = "Deck1"
    deck: Deck = deck_manager.new_deck()
    deck.name = deck_name
    op: OpChangesWithId = deck_manager.add_deck(deck)
    assert op.changes.deck
    act_deck: Optional[Deck] = deck_manager.by_name(deck_name)
    assert act_deck['name'] == deck_name
