import os
import tempfile
import unittest

from aqt import ProfileManager


class ProfileManagerTestCase(unittest.TestCase):

    def test_create_profile(self):
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

    def test_disable_auto_sync(self):
        pm = self.create_pm('Profile1')
        self.assertTrue(pm.auto_syncing_enabled())
        pm.profile['autoSync'] = False
        self.assertFalse(pm.auto_syncing_enabled())

    def create_pm(self, profile_name: str):
        tmp_dir = tempfile.mkdtemp()
        os.removedirs(tmp_dir)
        base_dir = ProfileManager.get_created_base_folder(tmp_dir)
        pm = ProfileManager(base=base_dir)
        pm.setupMeta()
        pm.create(profile_name)
        self.assertEqual(pm.profiles(), [profile_name])
        pm.openProfile(profile_name)
        pm.save()
        return pm
