from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QPushButton, QVBoxLayout

from src.module.thirdparty.PyQt6 import window

with window() as window:
    rows: int = 10
    columns: int = 2

    table: QTableWidget = QTableWidget(rows, columns)
    table.setHorizontalHeaderLabels(["Text", "Icon"])
    icon: QIcon = QIcon("info.png")
    for row in range(rows):
        text_item: QTableWidgetItem = QTableWidgetItem(f"Text {row}")
        table.setItem(row, 0, text_item)

        icon_item: QTableWidgetItem = QTableWidgetItem()
        icon_item.setIcon(icon)
        table.setItem(row, 1, icon_item)

    button: QPushButton = QPushButton("Clear")
    # noinspection PyUnresolvedReferences
    button.clicked.connect(lambda: table.clear())

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(table)
    layout.addWidget(button)

    window.setLayout(layout)
    window.setMinimumSize(500, 300)
