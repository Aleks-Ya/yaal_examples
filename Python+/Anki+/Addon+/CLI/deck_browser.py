# Doesn't work
import os
import sys
import tempfile
from argparse import Namespace

from anki.collection import Collection
from aqt import AnkiQt, AnkiApp, ProfileManager

tmp_dir = tempfile.mkdtemp()
os.removedirs(tmp_dir)
base_dir = ProfileManager.get_created_base_folder(tmp_dir)
pm = ProfileManager(base=base_dir)
pm.setupMeta()
profile = "Profile1"
pm.create(profile)
pm.setLang("en")
pm.openProfile(profile)

args = [f"base={base_dir}", f"profile={profile}"]
app = AnkiApp(args)
col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])
namespace = Namespace(
    safemode=False,
    profile=profile,
    base=base_dir,
    # path=None,
    web=False,
    # debug=False,
    # version=False,
    no_update_check=True
)
app.startingUp()
mw = AnkiQt(app, pm, col.backend, namespace, sys.argv)
mw.setupUI()
from aqt.deckbrowser import DeckBrowser
deck_browser: DeckBrowser = mw.deckBrowser
mw.close()
