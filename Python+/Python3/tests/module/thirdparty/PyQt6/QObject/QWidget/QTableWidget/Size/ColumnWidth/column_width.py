from PyQt6.QtWidgets import QTableWidget, QHBoxLayout

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers
from tests.module.thirdparty.PyQt6 import window

with window() as window:
    table: QTableWidget = create_table_with_headers()
    table.horizontalHeader().setMinimumSectionSize(0)

    column_name: int = 0
    assert table.columnWidth(column_name) == 100
    table.setColumnWidth(column_name, 50)

    column_age: int = 1
    assert table.columnWidth(column_age) == 100
    table.setColumnWidth(column_age, 30)

    column_city: int = 2
    assert table.columnWidth(column_city) == 100
    table.setColumnWidth(column_city, 800)

    layout: QHBoxLayout = QHBoxLayout()
    layout.addWidget(table)

    window.setLayout(layout)
    window.resize(1200, 300)
