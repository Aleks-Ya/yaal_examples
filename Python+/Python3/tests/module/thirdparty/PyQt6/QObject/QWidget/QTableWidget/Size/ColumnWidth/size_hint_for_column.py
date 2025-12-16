from PyQt6.QtWidgets import QTableWidget, QHBoxLayout, QTableWidgetItem

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers
from tests.module.thirdparty.PyQt6 import window

with window() as window:
    table: QTableWidget = create_table_with_headers()
    table.horizontalHeader().setMinimumSectionSize(0)

    column_name: int = 0
    assert table.columnWidth(column_name) == 100
    # assert table.sizeHintForColumn(column_name) == 43
    table.setColumnWidth(column_name, 50)
    assert table.columnWidth(column_name) == 50
    # assert table.sizeHintForColumn(column_name) == 43

    column_age: int = 1
    assert table.columnWidth(column_age) == 100
    assert table.sizeHintForColumn(column_age) == 24
    table.setColumnWidth(column_age, 30)
    assert table.columnWidth(column_age) == 30
    assert table.sizeHintForColumn(column_age) == 24

    column_city: int = 2
    assert table.columnWidth(column_city) == 100
    # assert table.sizeHintForColumn(column_city) == 792
    table.setColumnWidth(column_city, 800)
    assert table.columnWidth(column_city) == 800
    # assert table.sizeHintForColumn(column_city) == 792

    table.resizeColumnsToContents()
    # assert table.columnWidth(column_name) == 46
    # assert table.sizeHintForColumn(column_name) == 43
    # assert table.columnWidth(column_age) == 33
    assert table.sizeHintForColumn(column_age) == 24
    # assert table.columnWidth(column_city) == 792
    # assert table.sizeHintForColumn(column_city) == 792

    item02: QTableWidgetItem = table.item(0, column_city)
    item02.setText("Shorter text")
    # assert table.columnWidth(column_city) == 792
    # assert table.sizeHintForColumn(column_city) == 90

    layout: QHBoxLayout = QHBoxLayout()
    layout.addWidget(table)

    window.setLayout(layout)
    window.resize(1200, 300)
