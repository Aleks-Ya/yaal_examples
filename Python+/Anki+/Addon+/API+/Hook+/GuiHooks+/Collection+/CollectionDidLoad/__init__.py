from anki.collection import Collection
from aqt import gui_hooks
from aqt.utils import show_info

from ._common.disable import enabled


def __on_event(col: Collection):
    show_info(f"""
            Collection did load.
            note_count={len(col.find_notes("deck:*"))}
            """)


if enabled():
    gui_hooks.collection_did_load.append(__on_event)
