from aqt import gui_hooks, mw
from aqt.utils import show_info

from ._common.disable import enabled


def __on_event():
    show_info(f"""
            Profile did open.
            'mw'={mw}
            'mw.col'={mw.col}
            """)


if enabled():
    gui_hooks.profile_did_open.append(__on_event)
