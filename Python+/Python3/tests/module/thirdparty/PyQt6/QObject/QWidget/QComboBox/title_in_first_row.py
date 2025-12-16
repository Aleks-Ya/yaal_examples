from PyQt6.QtWidgets import QComboBox

from tests.module.thirdparty.PyQt6 import app

with app():
    combo_box: QComboBox = QComboBox()
    combo_box.addItems(["Choose animal", "Cat", "Dog", "Rabbit"])
    combo_box.model().item(0).setEnabled(False)
    combo_box.show()
