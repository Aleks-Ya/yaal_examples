# Doesn't work
import os
import sys
import tempfile
from argparse import Namespace

from anki.collection import Collection
from aqt import AnkiQt, AnkiApp, ProfileManager
from aqt.browser import Browser

tmp_dir = tempfile.mkdtemp()
os.removedirs(tmp_dir)
base_dir = ProfileManager.get_created_base_folder(tmp_dir)
pm = ProfileManager(base=base_dir)
pm.setupMeta()
pm.create("Profile1")
pm.setLang("en")

app = AnkiApp([])
_, full_name = tempfile.mkstemp()
col = Collection(path=full_name)
namespace = Namespace(
    safemode=False,
    profile="Profile1",
    # base=None,
    # path=None,
    web=False,
    # debug=False,
    # version=False,
    # no_update_check=False,
)
mw = AnkiQt(app, pm, col.backend, namespace, sys.argv)
browser = Browser(mw=mw)
browser.close()
mw.close()
