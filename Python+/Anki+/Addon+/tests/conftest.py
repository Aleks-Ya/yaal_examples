import tempfile
from pathlib import Path
from typing import Generator

import aqt
import pytest
from anki.collection import Collection
from anki.decks import DeckId, DeckManager
from anki.models import NoteType
from aqt import ProfileManager
from aqt.theme import ThemeManager
from mock.mock import MagicMock


@pytest.fixture
def profile_name() -> str:
    return "User1"


@pytest.fixture
def base_dir() -> Path:
    return Path(tempfile.mkdtemp(prefix="anki-base-dir"))


@pytest.fixture
def profile_manager(base_dir: Path, profile_name: str) -> ProfileManager:
    anki_base_dir: Path = ProfileManager.get_created_base_folder(str(base_dir))
    pm: ProfileManager = ProfileManager(base=anki_base_dir)
    pm.setupMeta()
    pm.create(profile_name)
    pm.openProfile(profile_name)
    pm.save()
    return pm


@pytest.fixture
def theme_manager(profile_manager: ProfileManager) -> ThemeManager:
    mw_mock: MagicMock = MagicMock()
    mw_mock.pm = profile_manager
    aqt.mw = mw_mock
    return ThemeManager()


@pytest.fixture
def col(profile_manager: ProfileManager) -> Generator[Collection, None, None]:
    collection_file: str = profile_manager.collectionPath()
    col: Collection = Collection(collection_file)
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


@pytest.fixture
def deck_manager(col: Collection) -> DeckManager:
    return col.decks
