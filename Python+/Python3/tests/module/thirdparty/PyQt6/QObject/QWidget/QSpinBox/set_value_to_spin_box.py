from PyQt6.QtWidgets import QSpinBox

from src.module.thirdparty.PyQt6 import app

with app():
    spin_box: QSpinBox = QSpinBox()
    spin_box.setValue(50)
    spin_box.show()
