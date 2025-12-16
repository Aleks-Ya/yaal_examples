from PyQt6.QtWidgets import QSpinBox

from tests.module.thirdparty.PyQt6 import app

with app():
    spin_box: QSpinBox = QSpinBox()
    spin_box.valueChanged.connect(lambda: print(f"Value changed: {spin_box.value()}"))
    spin_box.textChanged.connect(lambda: print(f"Text changed: {spin_box.value()}"))
    spin_box.editingFinished.connect(lambda: print(f"Editing finished: {spin_box.value()}"))
    spin_box.show()
