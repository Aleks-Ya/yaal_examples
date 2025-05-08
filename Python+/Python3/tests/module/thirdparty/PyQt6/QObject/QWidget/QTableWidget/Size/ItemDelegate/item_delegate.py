from PyQt6.QtCore import QModelIndex, QPoint
from PyQt6.QtWidgets import QTableWidget, QStyledItemDelegate

from src.module.thirdparty.PyQt6 import app

with app():
    table: QTableWidget = QTableWidget(2, 3)

    item_delegate: QStyledItemDelegate = table.itemDelegate()
    item_delegate_column: QStyledItemDelegate = table.itemDelegateForColumn(0)
    item_delegate_row: QStyledItemDelegate = table.itemDelegateForRow(0)
    index: QModelIndex = table.indexAt(QPoint(0, 0))
    item_delegate_index: QStyledItemDelegate = table.itemDelegateForIndex(index)

    assert item_delegate is not None
    assert item_delegate_column is None
    assert item_delegate_row is None
    assert item_delegate_index is not None

    table.show()
