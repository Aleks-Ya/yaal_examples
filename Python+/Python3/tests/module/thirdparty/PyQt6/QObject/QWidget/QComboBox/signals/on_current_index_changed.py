from typing import Callable

from PyQt6.QtWidgets import QComboBox

from src.module.thirdparty.PyQt6 import app


def __on_current_index_changed(index: int) -> None:
    print(f"Selected index: {index}")


with app():
    changed: Callable[[int], None] = __on_current_index_changed

    combo_box: QComboBox = QComboBox()
    combo_box.addItems(["Option 1", "Option 2", "Option 3"])
    # noinspection PyUnresolvedReferences
    combo_box.currentIndexChanged.connect(changed)
    combo_box.show()
