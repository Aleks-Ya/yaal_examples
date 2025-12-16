from PyQt6.QtGui import QPalette, QColor
from PyQt6.QtWidgets import QTableWidget, QHeaderView

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers
from tests.module.thirdparty.PyQt6 import app

with app() as app:
    table: QTableWidget = create_table_with_headers()
    header: QHeaderView = table.horizontalHeader()

    palette: QPalette = header.palette()
    role: QPalette.ColorRole = header.backgroundRole()
    background_color: QColor = palette.color(role)
    assert background_color.isValid()
    assert background_color.name() == '#fcfcfc'

    table.resize(600, 400)
    table.show()
