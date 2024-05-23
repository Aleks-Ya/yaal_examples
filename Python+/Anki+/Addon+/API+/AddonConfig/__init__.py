# Addon config
from typing import Optional, Any, List, Dict

from aqt import mw
from aqt.qt import *
from aqt.utils import showInfo


def _ui_action():
    config: Optional[dict[str, Any]] = mw.addonManager.getConfig(__name__)
    title: str = config['title']
    enabled: bool = config['enabled']
    room: int = config['room']
    fruits: List[str] = config['fruits']
    address: Dict[str, str] = config['address']
    city: str = config['address']['city']
    showInfo(f"""
        Title: {title}
        Enabled: {enabled}
        Room: {room}
        Fruits: {fruits}
        Address: {address}
        City: {city}
    """)


action = QAction("Addon Config", mw)
qconnect(action.triggered, _ui_action)
mw.form.menuTools.addAction(action)
