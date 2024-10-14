import os
import tempfile
from pathlib import Path

from pytest import raises
from anki.collection import Collection
from aqt import ProfileManager


def test_create_profile():
    tmp_dir: str = tempfile.mkdtemp()
    os.removedirs(tmp_dir)
    base_dir: Path = ProfileManager.get_created_base_folder(tmp_dir)
    pm: ProfileManager = ProfileManager(base=base_dir)
    pm.setupMeta()
    profile: str = "Profile1"
    pm.create(profile)
    assert pm.profiles() == [profile]
    pm.openProfile(profile)
    pm.save()


def test_disable_auto_sync():
    pm: ProfileManager = __create_pm('Profile1')
    assert pm.auto_syncing_enabled()
    pm.profile['autoSync'] = False
    assert not pm.auto_syncing_enabled()


def test_collection_path():
    profile: str = "Profile1"
    base_dir: Path = Path(tempfile.mkdtemp())

    pm: ProfileManager = ProfileManager(base=base_dir)
    with raises(TypeError):
        pm.collectionPath()

    pm.setupMeta()
    with raises(TypeError):
        pm.collectionPath()

    pm.create(profile)
    with raises(TypeError):
        pm.collectionPath()

    pm.openProfile(profile)
    exp_collection_path: str = f"{base_dir}/{profile}/collection.anki2"
    act_collection_path: str = pm.collectionPath()
    assert act_collection_path == exp_collection_path

    col: Collection = Collection(act_collection_path)
    assert col.note_count() == 0


def __create_pm(profile_name: str):
    tmp_dir: str = tempfile.mkdtemp()
    os.removedirs(tmp_dir)
    base_dir: Path = ProfileManager.get_created_base_folder(tmp_dir)
    pm: ProfileManager = ProfileManager(base=base_dir)
    pm.setupMeta()
    pm.create(profile_name)
    assert pm.profiles() == [profile_name]
    pm.openProfile(profile_name)
    pm.save()
    return pm
