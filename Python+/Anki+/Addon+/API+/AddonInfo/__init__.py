# Add item to "Main window -> Tools"
import os
import sys
from pathlib import Path

from PyQt6.QtGui import QAction
from aqt import mw, qconnect
from aqt.utils import showInfo


def _show_addon_info() -> None:
    showInfo(f"""\
    Addons folder={mw.addonManager.addonsFolder()}
    Addon name={os.path.basename(os.path.dirname(__file__))}
    Addon dir (os)={os.path.dirname(__file__)}
    Addon dir (Path)={Path(__file__).parent}
    sys.path={sys.path}
    """)


action = QAction("Show addon info", mw)
qconnect(action.triggered, _show_addon_info)
mw.form.menuTools.addAction(action)
