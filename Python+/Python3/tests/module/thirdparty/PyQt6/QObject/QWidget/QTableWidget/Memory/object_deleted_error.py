from pytest import raises
from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem

from tests.module.thirdparty.PyQt6 import app

# Reproduce "RuntimeError: wrapped C/C++ object of type QTableWidgetItem has been deleted"
with app():
    item: QTableWidgetItem = QTableWidgetItem("Item")
    table: QTableWidget = QTableWidget(1, 1)
    table.setItem(0, 0, item)
    table.clearContents()
    with raises(RuntimeError):
        item.type()
    with raises(RuntimeError):
        table.setItem(0, 0, item)
    table.show()
