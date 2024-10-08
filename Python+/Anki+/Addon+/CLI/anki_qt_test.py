import os
import tempfile
from argparse import Namespace
from pathlib import Path

from anki.collection import Collection
from aqt import AnkiQt, AnkiApp, ProfileManager


def test_anki_qt():
    profile: str = "Profile1"
    base_dir: Path = __base_dir()
    pm: ProfileManager = __profile_manager(base_dir, profile)

    col: Collection = Collection(pm.collectionPath())
    col.close()  # prevent "collection already open"
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
    args: list[str] = [f"base={base_dir}", f"profile={profile}"]
    app: AnkiApp = AnkiApp(args)
    app.startingUp()
    mw: AnkiQt = AnkiQt(app, pm, col.backend, namespace, [])
    # mw.setupProfile()
    # mw.loadProfile()
    mw.close()


def __base_dir() -> Path:
    tmp_dir: str = tempfile.mkdtemp()
    os.removedirs(tmp_dir)
    base_dir: Path = ProfileManager.get_created_base_folder(tmp_dir)
    return base_dir


def __profile_manager(base_dir, profile) -> ProfileManager:
    pm: ProfileManager = ProfileManager(base=base_dir)
    pm.setupMeta()
    pm.create(profile)
    pm.setLang("en")
    pm.openProfile(profile)
    return pm
