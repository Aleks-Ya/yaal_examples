from typing import Callable

from PyQt6.QtWidgets import QComboBox

from tests.module.thirdparty.PyQt6 import app


# "activated" reacts only on user actions and ignore programmatic changes
def __on_activated(index: int) -> None:
    print(f"Selected index: {index}")


with app():
    changed: Callable[[int], None] = __on_activated

    combo_box: QComboBox = QComboBox()
    combo_box.addItems(["Option 1", "Option 2", "Option 3"])
    # noinspection PyUnresolvedReferences
    combo_box.activated.connect(changed)
    combo_box.show()
