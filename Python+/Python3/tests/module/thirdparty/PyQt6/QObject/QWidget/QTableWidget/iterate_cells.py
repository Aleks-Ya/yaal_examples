from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem

from tests.module.thirdparty.PyQt6 import app

with app():
    table: QTableWidget = QTableWidget(2, 2)
    table.setItem(0, 0, QTableWidgetItem("Item 00"))
    table.setItem(0, 1, QTableWidgetItem("Item 01"))
    table.setItem(1, 0, QTableWidgetItem("Item 10"))
    table.setItem(1, 1, QTableWidgetItem("Item 11"))

    for row in range(table.rowCount()):
        for column in range(table.columnCount()):
            item: QTableWidgetItem = table.item(row, column)
            if item is not None:
                print(item.text())
