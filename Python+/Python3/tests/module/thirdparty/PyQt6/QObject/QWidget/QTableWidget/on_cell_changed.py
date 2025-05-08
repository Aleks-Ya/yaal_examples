from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem

from src.module.thirdparty.PyQt6 import app


def __on_cell_changed(row: int, column: int):
    print(f"on_cell_changed: {row}, {column}")


with app():
    table: QTableWidget = QTableWidget(2, 2)
    table.cellChanged.connect(__on_cell_changed)

    print("Cell changed programmatically:")
    table.setItem(0, 0, QTableWidgetItem("Item 00"))
    table.setItem(0, 1, QTableWidgetItem("Item 01"))
    table.setItem(1, 0, QTableWidgetItem("Item 10"))
    table.setItem(1, 1, QTableWidgetItem("Item 11"))

    print("\nCell changed by user:")

    table.show()
