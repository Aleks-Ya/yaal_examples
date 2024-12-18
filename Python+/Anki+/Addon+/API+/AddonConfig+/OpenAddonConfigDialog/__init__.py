from typing import Optional

from aqt import mw, dialogs
from aqt.addons import AddonManager, ConfigEditor, AddonsDialog

from ._common.disable import enabled
from ._common import menu


def __open_addons_config_dialog():
    am: AddonManager = mw.addonManager
    am.onAddonsDialog()


def __open_current_addon_config_dialog_new():
    am: AddonManager = mw.addonManager
    module: str = __name__
    ad: AddonsDialog = AddonsDialog(am)
    conf: Optional[dict[str, any]] = am.getConfig(module)
    ConfigEditor(ad, module, conf)


def __open_current_addon_config_dialog_existing():
    am: AddonManager = mw.addonManager
    module: str = __name__
    addons_dialog: AddonsDialog = dialogs.open("AddonsDialog", am)
    conf: Optional[dict[str, any]] = am.getConfig(module)
    ConfigEditor(addons_dialog, module, conf)


if enabled():
    menu.add_mw_menu_item("Open config dialog for all addons", __open_addons_config_dialog)
    menu.add_mw_menu_item("Open config dialog for current addon (existing AddonsDialog)",
                          __open_current_addon_config_dialog_existing)
    menu.add_mw_menu_item("Open config dialog for current addon (new AddonsDialog)",
                          __open_current_addon_config_dialog_new)
