from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QSpinBox

from tests.module.thirdparty.PyQt6 import app

with app():
    spin_box_0: QSpinBox = QSpinBox()
    spin_box_0.setMinimum(18)
    spin_box_0.setMaximum(23)

    spin_box_1: QSpinBox = QSpinBox()
    spin_box_1.setMinimum(18)
    spin_box_1.setMaximum(23)

    table: QTableWidget = QTableWidget(2, 2)
    table.setHorizontalHeaderLabels(["Name", "Age"])

    table.setItem(0, 0, QTableWidgetItem("John"))
    table.setCellWidget(0, 1, spin_box_0)

    table.setItem(1, 0, QTableWidgetItem("Mary"))
    table.setCellWidget(1, 1, spin_box_1)

    table.show()
