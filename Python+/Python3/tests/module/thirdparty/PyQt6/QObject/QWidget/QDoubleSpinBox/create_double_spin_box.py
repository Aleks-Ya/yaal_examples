from PyQt6.QtWidgets import QDoubleSpinBox

from tests.module.thirdparty.PyQt6 import app

with app():
    spin_box: QDoubleSpinBox = QDoubleSpinBox()
    spin_box.setPrefix("$")
    spin_box.setDecimals(4)
    spin_box.setMinimum(1.5)
    spin_box.setMaximum(7.6)
    spin_box.setSingleStep(0.01)
    spin_box.show()
