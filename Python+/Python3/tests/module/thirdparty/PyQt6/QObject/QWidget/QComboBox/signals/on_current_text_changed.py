from typing import Callable

from PyQt6.QtWidgets import QComboBox

from tests.module.thirdparty.PyQt6 import app


def __on_current_text_changed(text: str) -> None:
    print(f"Selected text: {text}")


with app():
    changed: Callable[[str], None] = __on_current_text_changed

    combo_box: QComboBox = QComboBox()
    combo_box.addItems(["Option 1", "Option 2", "Option 3"])
    # noinspection PyUnresolvedReferences
    combo_box.currentTextChanged.connect(changed)
    combo_box.show()
