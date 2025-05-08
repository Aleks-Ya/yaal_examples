from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QHeaderView

from src.module.thirdparty.PyQt6 import app

with app():
    icon: QIcon = QIcon("info.png")

    item_icon_and_text: QTableWidgetItem = QTableWidgetItem("Hello " * 8)
    item_icon_and_text.setIcon(icon)
    item_icon_and_text.setFlags(
        item_icon_and_text.flags() & ~Qt.ItemFlag.ItemIsEditable & ~Qt.ItemFlag.ItemIsSelectable)

    item_icon_only: QTableWidgetItem = QTableWidgetItem()
    item_icon_only.setIcon(icon)
    item_icon_only.setData(Qt.ItemDataRole.DisplayRole, None)
    item_icon_only.setFlags(item_icon_only.flags() & ~Qt.ItemFlag.ItemIsEditable & ~Qt.ItemFlag.ItemIsSelectable)

    row0: int = 0
    column0: int = 0
    column1: int = 1
    column2: int = 2
    table: QTableWidget = QTableWidget(2, 3)
    table.setItem(row0, column0, item_icon_and_text)
    table.setItem(row0, column1, item_icon_only)

    header: QHeaderView = table.horizontalHeader()
    header.setMinimumSectionSize(0)
    header.setSectionResizeMode(column0, QHeaderView.ResizeMode.ResizeToContents)
    header.setSectionResizeMode(column1, QHeaderView.ResizeMode.ResizeToContents)
    header.setSectionResizeMode(column2, QHeaderView.ResizeMode.Stretch)

    table.setSizeAdjustPolicy(QTableWidget.SizeAdjustPolicy.AdjustToContents)
    table.show()
