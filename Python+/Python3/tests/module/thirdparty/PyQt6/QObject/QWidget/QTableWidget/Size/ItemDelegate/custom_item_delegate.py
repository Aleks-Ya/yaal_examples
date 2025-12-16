from PyQt6.QtCore import QSize
from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QStyledItemDelegate

from tests.module.thirdparty.PyQt6 import app


class CustomItemDelegate(QStyledItemDelegate):
    def sizeHint(self, option, index):
        hint: QSize = super().sizeHint(option, index)
        hint.setWidth(200)
        return hint


with app():
    item: QTableWidgetItem = QTableWidgetItem("Hello")
    item_delegate: CustomItemDelegate = CustomItemDelegate()

    table: QTableWidget = QTableWidget(1, 1)
    table.setItem(0, 0, item)
    table.setItemDelegate(item_delegate)
    table.resizeColumnsToContents()
    table.show()
