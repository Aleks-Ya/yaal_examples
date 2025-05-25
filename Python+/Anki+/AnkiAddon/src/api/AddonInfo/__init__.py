# Add item to "Main window -> Tools"
import os
import sys
from pathlib import Path

from aqt import mw
from aqt.utils import show_info

from ._common.disable import enabled
from ._common import menu


def __show_addon_info() -> None:
    show_info(f"""\
    Addons folder={mw.addonManager.addonsFolder()}
    Addon name={os.path.basename(os.path.dirname(__file__))}
    Addon dir (os)={os.path.dirname(__file__)}
    Addon dir (Path)={Path(__file__).parent}
    sys.path={sys.path}
    """)


if enabled():
    menu.add_mw_menu_item("Show addon info", __show_addon_info)
