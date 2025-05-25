# Doesn't work
import os
import sys
import tempfile
from argparse import Namespace
from pathlib import Path

from anki.collection import Collection
from anki.notes import Note
from aqt import AnkiQt, AnkiApp, ProfileManager
from aqt.editcurrent import EditCurrent

tmp_dir: str = tempfile.mkdtemp()
os.removedirs(tmp_dir)
base_dir: Path = ProfileManager.get_created_base_folder(tmp_dir)
pm: ProfileManager = ProfileManager(base=base_dir)
pm.setupMeta()
profile: str = "Profile1"
pm.create(profile)
pm.setLang("en")
pm.openProfile(profile)

args: list[str] = [f"base={base_dir}", f"profile={profile}"]
app: AnkiApp = AnkiApp(args)
col: Collection = Collection(os.path.join(base_dir, "collection.anki2"))
col.save()
col.close()
namespace: Namespace = Namespace(
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
mw: AnkiQt = AnkiQt(app, pm, col.backend, namespace, sys.argv)
mw.setupUI()
mw.finish_ui_setup()
mw.loadProfile()
mw.loadCollection()
# mw.col = col
# browser = Browser(mw=mw)
# browser.close()

note: Note = col.newNote()
note['Front'] = 'one'
note['Back'] = 'two'
col.addNote(note)

mw.moveToState("review")
# mw.setupReviewer()
mw.reviewer.card._note = note
edit_current: EditCurrent = EditCurrent(mw)

# editor: Editor = Editor(mw)

mw.close()
