from PyQt6.QtWidgets import QTableWidget, QPushButton

from src.module.thirdparty.PyQt6 import vbox


def __add_row() -> None:
    table.insertRow(table.rowCount())


def __remove_row() -> None:
    current_row: int = table.currentRow()
    if current_row != -1:
        table.removeRow(current_row)


with vbox() as layout:
    table: QTableWidget = QTableWidget(0, 3)
    table.setHorizontalHeaderLabels(["Column 1", "Column 2", "Column 3"])

    add_button: QPushButton = QPushButton("Add Row")
    add_button.clicked.connect(__add_row)

    remove_button: QPushButton = QPushButton("Remove Row")
    remove_button.clicked.connect(__remove_row)

    layout.addWidget(table)
    layout.addWidget(add_button)
    layout.addWidget(remove_button)
