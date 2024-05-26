import json
import unittest
from pathlib import Path
from tempfile import TemporaryDirectory
from typing import Dict

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

    def test_read_config(self):
        with TemporaryDirectory() as td:
            f: Path = Path(td, 'config.json')
            data: str = '{"param1": "value1"}'
            json_obj: Dict[str, object] = json.loads(data)
            with open(f, 'w') as fp:
                json.dump(json_obj, fp, indent=2)
            wm = MagicMock()
            wm.pm.addonFolder.return_value = td
            adm = AddonManager(wm)
            config = adm.getConfig("")
            value = config['param1']
            self.assertEqual(value, 'value1')


if __name__ == '__main__':
    unittest.main()
