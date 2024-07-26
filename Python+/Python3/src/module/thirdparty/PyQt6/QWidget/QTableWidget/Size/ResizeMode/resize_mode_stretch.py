from PyQt6.QtWidgets import QApplication, QTableWidget, QWidget, QHBoxLayout, QHeaderView

from src.module.thirdparty.PyQt6.QWidget.QTableWidget.data import create_table_with_headers

app: QApplication = QApplication([])

window: QWidget = QWidget()
layout: QHBoxLayout = QHBoxLayout()

table: QTableWidget = create_table_with_headers()

table.horizontalHeader().setSectionResizeMode(QHeaderView.ResizeMode.Stretch)
table.verticalHeader().setSectionResizeMode(QHeaderView.ResizeMode.Stretch)

layout.addWidget(table)

window.setLayout(layout)
window.setFixedWidth(500)
window.setFixedHeight(500)

window.show()
app.exec()
