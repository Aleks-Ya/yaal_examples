from PyQt6.QtWidgets import QApplication, QTableWidget, QWidget, QHBoxLayout, QHeaderView

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers

app: QApplication = QApplication([])

table: QTableWidget = create_table_with_headers()
table.horizontalHeader().setSectionResizeMode(QHeaderView.ResizeMode.ResizeToContents)
table.verticalHeader().setSectionResizeMode(QHeaderView.ResizeMode.ResizeToContents)

layout: QHBoxLayout = QHBoxLayout()
layout.addWidget(table)

window: QWidget = QWidget()
window.setLayout(layout)
window.adjustSize()
window.show()

app.exec()
