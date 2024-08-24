from PyQt6.QtWidgets import QTableWidget

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers
from src.module.thirdparty.PyQt6 import app

with app() as app:
    table: QTableWidget = create_table_with_headers()

    table.verticalHeader().setStyleSheet("""
        QHeaderView::section {
            background-color: lightgray;
            color: black;
            font-weight: bold;
            border: 1px solid black;
        }
    """)

    table.resize(600, 400)
    table.show()
