from aqt import gui_hooks, mw
from aqt.utils import showInfo


def on_init():
    showInfo(f"""
            MainWindow did init.
            'mw'={mw}
            'mw.col'={mw.col}
            """)


# gui_hooks.main_window_did_init.append(on_init)
