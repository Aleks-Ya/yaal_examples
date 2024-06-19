from aqt import gui_hooks, mw
from aqt.utils import showInfo

from ._common.disable import enabled


def _on_event():
    showInfo(f"""
            Profile did open.
            'mw'={mw}
            'mw.col'={mw.col}
            """)


if enabled():
    gui_hooks.profile_did_open.append(_on_event)
