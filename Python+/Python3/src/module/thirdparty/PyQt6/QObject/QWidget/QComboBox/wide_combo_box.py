from PyQt6.QtWidgets import QComboBox

from src.module.thirdparty.PyQt6 import app

with app():
    combo_box: QComboBox = QComboBox()
    combo_box.addItems([
        "Wide wide wide wide wide wide wide wide wide wide wide wide wide wide wide wide wide item",
        "Short item"
    ])
    combo_box.show()
