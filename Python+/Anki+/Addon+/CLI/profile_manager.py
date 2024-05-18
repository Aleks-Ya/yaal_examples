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
        pm.create("Profile1")
        self.assertEqual(pm.profiles(), ["Profile1"])
