from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QApplication, QTableWidget, QWidget, QHBoxLayout

from src.module.thirdparty.PyQt6.QWidget.QTableWidget.data import create_table_with_headers

app: QApplication = QApplication([])

window: QWidget = QWidget()
layout: QHBoxLayout = QHBoxLayout()

table: QTableWidget = create_table_with_headers()

table.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)
table.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)

table_width: int = table.verticalHeader().width() + table.horizontalHeader().length()
table_height: int = table.horizontalHeader().height() + table.verticalHeader().length()
table.setFixedSize(table_width, table_height)

layout.addWidget(table)

window.setLayout(layout)
window.setFixedWidth(500)
window.setFixedHeight(500)

window.show()
app.exec()
