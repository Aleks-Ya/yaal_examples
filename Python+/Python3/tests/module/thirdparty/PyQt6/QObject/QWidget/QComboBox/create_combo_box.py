from PyQt6.QtWidgets import QComboBox

from tests.module.thirdparty.PyQt6 import app

with app():
    combo_box: QComboBox = QComboBox()
    combo_box.addItem("Option 1")
    combo_box.addItems(["Option 2", "Option 3"])
    combo_box.show()
