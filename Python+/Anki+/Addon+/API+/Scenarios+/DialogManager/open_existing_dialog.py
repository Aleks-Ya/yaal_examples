from aqt import mw, dialogs
from aqt.addons import AddonManager

from ._common.disable import enabled
from ._common import menu


def __on_show_addons_dialog():
    addons_manager: AddonManager = mw.addonManager
    dialogs.open("AddonsDialog", addons_manager)

def init():
    if enabled(False):
        menu.add_mw_menu_item("Show AddonsDialog by DialogManager", __on_show_addons_dialog)
