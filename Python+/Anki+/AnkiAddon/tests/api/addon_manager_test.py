import json
from pathlib import Path
from tempfile import TemporaryDirectory
from typing import Dict, Optional, Any

from aqt.addons import AddonManager
from mock.mock import MagicMock


def test_create_addon_manager():
    with TemporaryDirectory() as td:
        mw: MagicMock = MagicMock()
        mw.pm.addonFolder.return_value = td
        adm: AddonManager = AddonManager(mw)
        all_addons: list[str] = adm.allAddons()
        assert len(all_addons) == 0


def test_read_config():
    with TemporaryDirectory() as td:
        f: Path = Path(td, 'config.json')
        data: str = '{"param1": "value1"}'
        json_obj: Dict[str, object] = json.loads(data)
        with open(f, 'w') as fp:
            # noinspection PyTypeChecker
            json.dump(json_obj, fp, indent=2)
        mw: MagicMock = MagicMock()
        mw.pm.addonFolder.return_value = td
        adm: AddonManager = AddonManager(mw)
        config: Optional[dict[str, Any]] = adm.getConfig("")
        value: object = config['param1']
        assert value == 'value1'


def test_addon_from_module():
    with TemporaryDirectory() as td:
        addon_name: str = "addon1"
        addon_dir: Path = Path(td).joinpath(addon_name)
        addon_dir.mkdir()
        init_path: Path = addon_dir.joinpath('__init__.py')
        init_path.write_text('print("abc")')
        mw: MagicMock = MagicMock()
        mw.pm.addonFolder.return_value = td
        adm: AddonManager = AddonManager(mw)
        all_addons: list[str] = adm.allAddons()
        assert len(all_addons) == 1
        addon_name_act: str = adm.addon_from_module(addon_name)
        assert addon_name == addon_name_act


def test_web_exports():
    with TemporaryDirectory() as td:
        addon_name: str = "addon1"
        addon_dir: Path = Path(td).joinpath(addon_name)
        addon_dir.mkdir()
        init_path: Path = addon_dir.joinpath('__init__.py')
        init_path.write_text('print("abc")')
        mw: MagicMock = MagicMock()
        mw.pm.addonFolder.return_value = td
        adm: AddonManager = AddonManager(mw)
        web_exports_exp: str = "web"
        adm.setWebExports(addon_name, web_exports_exp)
        web_exports_act: str = adm.getWebExports(addon_name)
        assert web_exports_exp == web_exports_act
