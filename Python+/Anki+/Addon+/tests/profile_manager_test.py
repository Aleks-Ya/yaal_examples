import os
import tempfile
from pathlib import Path

import pytest
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


def test_disable_auto_sync(profile_manager: ProfileManager):
    assert profile_manager.auto_syncing_enabled()
    profile_manager.profile['autoSync'] = False
    assert not profile_manager.auto_syncing_enabled()


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


def test_last_loaded_profile_name():
    tmp_dir: str = tempfile.mkdtemp()
    os.removedirs(tmp_dir)
    base_dir: Path = ProfileManager.get_created_base_folder(tmp_dir)
    pm: ProfileManager = ProfileManager(base=base_dir)
    with pytest.raises(AttributeError):
        pm.last_loaded_profile_name()
    pm.setupMeta()
    assert pm.last_loaded_profile_name() == None
    profile: str = "Profile1"
    pm.create(profile)
    assert pm.last_loaded_profile_name() == None
    pm.openProfile(profile)
    assert pm.last_loaded_profile_name() == profile
    pm.save()
    assert pm.last_loaded_profile_name() == profile
