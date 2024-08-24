import os
import tempfile
import unittest

import aqt
from aqt import ProfileManager
from aqt.theme import ThemeManager
from anki.collection import Collection
from anki.decks import DeckId
from anki.models import NotetypeDict
from mock.mock import MagicMock


class TestCollection(unittest.TestCase):
    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])
        self.basic_note_type: NotetypeDict = self.col.models.by_name('Basic')
        self.deck_id: DeckId = self.col.decks.get_current_id()

    def test_create_empty_collection(self):
        tmp_dir = tempfile.mkdtemp()
        os.removedirs(tmp_dir)
        base_dir = ProfileManager.get_created_base_folder(tmp_dir)
        pm = ProfileManager(base=base_dir)
        pm.setupMeta()
        profile = "Profile1"
        pm.create(profile)
        self.assertEqual(pm.profiles(), [profile])
        pm.openProfile(profile)
        pm.save()

        mw_mock: MagicMock = MagicMock()
        mw_mock.pm = pm
        aqt.mw = mw_mock

        tm = ThemeManager()
        tm.apply_style()

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()
