from aqt import gui_hooks, mw
from aqt.utils import showInfo


def on_init():
    showInfo(f"""
            Profile did open.
            'mw'={mw}
            'mw.col'={mw.col}
            """)


# gui_hooks.profile_did_open.append(on_init)
