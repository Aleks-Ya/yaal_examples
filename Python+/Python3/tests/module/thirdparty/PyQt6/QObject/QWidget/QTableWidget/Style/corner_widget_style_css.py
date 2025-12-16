from PyQt6.QtWidgets import QTableWidget

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers
from tests.module.thirdparty.PyQt6 import app

with app() as app:
    table: QTableWidget = create_table_with_headers()

    table.setStyleSheet("""
        QTableCornerButton::section {
            border: 3px dotted blue;
            background: red;
        }
    """)

    table.resize(600, 400)
    table.show()
