from PyQt6.QtWidgets import QTableWidget

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers
from src.module.thirdparty.PyQt6 import app

with app() as app:
    table: QTableWidget = create_table_with_headers()

    table.horizontalHeader().setStyleSheet("""
        QHeaderView::section {
            background-color: yellow;
            color: black;
        }
        QHeaderView::section {
            background-color: green;
            color: red;
        }
    """)

    assert table.horizontalHeader().styleSheet() == """
        QHeaderView::section {
            background-color: yellow;
            color: black;
        }
        QHeaderView::section {
            background-color: green;
            color: red;
        }
    """

    table.resize(600, 400)
    table.show()
