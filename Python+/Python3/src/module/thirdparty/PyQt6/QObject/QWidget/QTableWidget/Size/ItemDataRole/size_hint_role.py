from PyQt6.QtCore import Qt, QSize
from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem

from src.module.thirdparty.PyQt6 import app


class SizeHintItem(QTableWidgetItem):
    def __init__(self, text, size_hint: QSize):
        super().__init__(text)
        self.size_hint = size_hint

    def data(self, role):
        if role == Qt.ItemDataRole.SizeHintRole:
            return self.size_hint
        return super().data(role)


with app():
    item: SizeHintItem = SizeHintItem("Hello", QSize(200, 100))
    table: QTableWidget = QTableWidget(1, 1)
    table.setItem(0, 0, item)
    table.resizeColumnsToContents()
    table.resizeRowsToContents()
    table.show()
