from PyQt6.QtGui import QPalette
from PyQt6.QtWidgets import QTableWidget, QAbstractButton

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers
from tests.module.thirdparty.PyQt6 import app

with app() as app:
    table: QTableWidget = create_table_with_headers()
    palette: QPalette = table.palette()
    for role in QPalette.ColorRole:
        color = palette.color(role)
        print(f"{role.name}: {color.name()}")

    table.resize(600, 400)
    table.show()
