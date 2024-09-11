import os

from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QHBoxLayout

from src.module.thirdparty.PyQt6 import window

with window() as window:
    print(f"PID: {os.getpid()}")

    rows: int = 20_000
    columns: int = 2

    table: QTableWidget = QTableWidget(rows, columns)
    icon: QIcon = QIcon("info.png")
    for row in range(rows):
        text_item: QTableWidgetItem = QTableWidgetItem(f"Text {row}")
        table.setItem(row, 0, text_item)

        icon_item: QTableWidgetItem = QTableWidgetItem()
        icon_item.setIcon(icon)
        table.setItem(row, 1, icon_item)

    layout: QHBoxLayout = QHBoxLayout()
    layout.addWidget(table)

    window.setLayout(layout)
    window.setMinimumSize(500, 300)
