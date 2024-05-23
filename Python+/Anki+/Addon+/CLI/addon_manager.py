import unittest
from tempfile import TemporaryDirectory

from aqt.addons import AddonManager
from mock.mock import MagicMock


class AddonManagerTestCase(unittest.TestCase):

    def test_create_addon_manager(self):
        with TemporaryDirectory() as td:
            wm = MagicMock()
            wm.pm.addonFolder.return_value = td
            adm = AddonManager(wm)
            all_addons = adm.allAddons()
            self.assertEqual(len(all_addons), 0)


if __name__ == '__main__':
    unittest.main()
