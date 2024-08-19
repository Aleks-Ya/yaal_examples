from PyQt6.QtWidgets import QCheckBox

from src.module.thirdparty.PyQt6 import app


def on_state_changed(state: int):
    print(f"Checkbox state: {state}, Is checked: {checkbox.isChecked()}")


with app():
    checkbox: QCheckBox = QCheckBox("Check me!")
    checkbox.stateChanged.connect(on_state_changed)
    checkbox.show()
