import tempfile

import pytest
from anki.collection import Collection
from anki.decks import DeckId
from anki.models import NoteType


@pytest.fixture
def col() -> Collection:
    col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])
    yield col
    col.close()


@pytest.fixture
def basic_note_type(col: Collection) -> NoteType:
    return col.models.by_name('Basic')


@pytest.fixture
def cloze_note_type(col: Collection) -> NoteType:
    return col.models.by_name('Cloze')


@pytest.fixture
def deck_id(col: Collection) -> DeckId:
    return col.decks.get_current_id()
