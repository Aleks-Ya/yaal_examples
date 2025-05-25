from anki.collection import Collection
from aqt import mw, gui_hooks, QAction, qconnect
from aqt.utils import show_info

from .formatter.strings import upper_case


def __show_message() -> None:
    text: str = upper_case("abc")
    show_info(f"CythonAddon: {text}")


def __initialize(_: Collection):
    action: QAction = QAction("CythonAddon (packaged): execute Cython function", mw)
    qconnect(action.triggered, __show_message)
    mw.form.menuTools.addAction(action)


gui_hooks.collection_did_load.append(__initialize)
