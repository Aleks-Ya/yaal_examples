from PyQt6.QtWidgets import QTableWidget

from src.module.thirdparty.PyQt6 import app
from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers

with app():
    table: QTableWidget = create_table_with_headers()
    assert not table.isSortingEnabled()
    table.setSortingEnabled(True)
    assert table.isSortingEnabled()
    table.show()
