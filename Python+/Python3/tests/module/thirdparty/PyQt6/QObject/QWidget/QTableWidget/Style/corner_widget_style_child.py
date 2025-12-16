from PyQt6.QtWidgets import QTableWidget, QAbstractButton

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers
from tests.module.thirdparty.PyQt6 import app

with app() as app:
    table: QTableWidget = create_table_with_headers()
    assert table.isCornerButtonEnabled()
    assert table.cornerWidget() is None

    corner_button: QAbstractButton = table.findChild(QAbstractButton)
    corner_button.setStyleSheet("background-color: yellow;")

    table.resize(600, 400)
    table.show()
