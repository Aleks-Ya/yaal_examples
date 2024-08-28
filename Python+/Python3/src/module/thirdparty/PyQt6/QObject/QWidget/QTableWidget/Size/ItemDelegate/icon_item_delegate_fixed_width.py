from PyQt6.QtCore import QSize, Qt
from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QStyledItemDelegate, QHeaderView

from src.module.thirdparty.PyQt6 import app


class CustomItemDelegate(QStyledItemDelegate):
    def sizeHint(self, option, index):
        hint: QSize = super().sizeHint(option, index)
        if index.column() == 1:
            hint.setWidth(22)
        return hint


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

    item_delegate: CustomItemDelegate = CustomItemDelegate()

    row0: int = 0
    column0: int = 0
    column1: int = 1
    column2: int = 2
    column3: int = 3
    table: QTableWidget = QTableWidget(2, 4)
    table.setItem(row0, column0, item_icon_and_text)
    table.setItem(row0, column1, item_icon_only)
    table.setItem(row0, column2, QTableWidgetItem("Stretchable column"))
    table.setItem(row0, column3, QTableWidgetItem("Resize to content column"))
    table.setItemDelegate(item_delegate)

    header: QHeaderView = table.horizontalHeader()
    header.setMinimumSectionSize(0)
    header.setSectionResizeMode(column0, QHeaderView.ResizeMode.ResizeToContents)
    header.setSectionResizeMode(column1, QHeaderView.ResizeMode.Fixed)
    header.setSectionResizeMode(column2, QHeaderView.ResizeMode.Stretch)
    header.setSectionResizeMode(column3, QHeaderView.ResizeMode.ResizeToContents)

    table.setSizeAdjustPolicy(QTableWidget.SizeAdjustPolicy.AdjustToContents)
    table.resizeColumnsToContents()
    table.resizeRowsToContents()
    table.show()
