from PyQt6.QtWidgets import QTableWidget, QVBoxLayout

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers
from src.module.thirdparty.PyQt6 import window

with window() as window:
    layout: QVBoxLayout = QVBoxLayout()

    table_wrap_on: QTableWidget = create_table_with_headers()
    table_wrap_on.setSizeAdjustPolicy(QTableWidget.SizeAdjustPolicy.AdjustToContents)
    assert table_wrap_on.wordWrap()
    table_wrap_on.resizeRowsToContents()
    table_wrap_on.adjustSize()

    table_wrap_off: QTableWidget = create_table_with_headers()
    table_wrap_off.setSizeAdjustPolicy(QTableWidget.SizeAdjustPolicy.AdjustToContents)
    table_wrap_off.setWordWrap(False)
    assert not table_wrap_off.wordWrap()
    table_wrap_off.resizeRowsToContents()
    table_wrap_off.adjustSize()

    layout.addWidget(table_wrap_on)
    layout.addWidget(table_wrap_off)
    window.setLayout(layout)
    window.show()
