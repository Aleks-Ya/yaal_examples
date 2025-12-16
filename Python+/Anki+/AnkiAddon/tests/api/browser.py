# Doesn't work
import os
import tempfile
from argparse import Namespace
from pathlib import Path

from aqt import AnkiQt, AnkiApp, ProfileManager, setupLangAndBackend
from aqt.browser import Browser

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
app.startingUp()
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
back = setupLangAndBackend(pm, app, None, False)
mw: AnkiQt = AnkiQt(app, pm, back, namespace, args)
mw.setupProfile()
browser: Browser = Browser(mw=mw)
# browser.show()
# browser.form.tableView.showColumn()
# browser.close()
# mw.close()
