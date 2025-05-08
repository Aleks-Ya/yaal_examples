from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem

from src.module.thirdparty.PyQt6 import app

with app():
    rows: int = 3
    columns: int = 3

    table: QTableWidget = QTableWidget(rows, columns)
    table.setItem(0, 0, QTableWidgetItem("Item 1"))
    table.setItem(0, 1, QTableWidgetItem("Item 2"))
    table.setItem(0, 2, QTableWidgetItem("Item 3"))
    table.show()

    table.setCurrentCell(1, 1)
