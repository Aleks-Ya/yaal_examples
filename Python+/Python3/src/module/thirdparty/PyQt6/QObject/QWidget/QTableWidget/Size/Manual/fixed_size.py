from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QTableWidget, QHBoxLayout

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers
from src.module.thirdparty.PyQt6 import window

with window() as window:
    table: QTableWidget = create_table_with_headers()
    table.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)
    table.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)

    table_width: int = table.verticalHeader().width() + table.horizontalHeader().length()
    table_height: int = table.horizontalHeader().height() + table.verticalHeader().length()
    table.setFixedSize(table_width, table_height)

    layout: QHBoxLayout = QHBoxLayout()
    layout.addWidget(table)

    window.setLayout(layout)
    window.adjustSize()
