from PyQt6.QtWidgets import QTableWidget, QHBoxLayout, QHeaderView

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers
from tests.module.thirdparty.PyQt6 import window

with window() as window:
    table: QTableWidget = create_table_with_headers()
    table.horizontalHeader().setSectionResizeMode(QHeaderView.ResizeMode.Interactive)
    table.verticalHeader().setSectionResizeMode(QHeaderView.ResizeMode.Interactive)

    layout: QHBoxLayout = QHBoxLayout()
    layout.addWidget(table)

    window.setLayout(layout)
    window.adjustSize()
