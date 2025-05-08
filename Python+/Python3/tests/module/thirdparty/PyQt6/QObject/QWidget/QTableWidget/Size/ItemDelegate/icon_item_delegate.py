from PyQt6.QtCore import QSize
from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QStyledItemDelegate

from src.module.thirdparty.PyQt6 import app


class CustomItemDelegate(QStyledItemDelegate):
    def sizeHint(self, option, index):
        hint: QSize = super().sizeHint(option, index)
        hint.setWidth(200)
        return hint


with app():
    icon: QIcon = QIcon("info.png")

    item_icon_and_text: QTableWidgetItem = QTableWidgetItem("Hello " * 8)
    item_icon_and_text.setIcon(icon)

    item_icon_only: QTableWidgetItem = QTableWidgetItem()
    item_icon_only.setIcon(icon)

    item_delegate: CustomItemDelegate = CustomItemDelegate()

    row0: int = 0
    column0: int = 0
    column1: int = 1
    column2: int = 2
    table: QTableWidget = QTableWidget(2, 3)
    table.setItem(row0, column0, item_icon_and_text)
    table.setItem(row0, column1, item_icon_only)
    table.setItemDelegate(item_delegate)
    table.resizeColumnsToContents()
    table.resizeRowsToContents()
    table.show()
