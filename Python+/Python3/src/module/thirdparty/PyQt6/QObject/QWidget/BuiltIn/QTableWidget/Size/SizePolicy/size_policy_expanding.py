from PyQt6.QtWidgets import QApplication, QTableWidget, QWidget, QHBoxLayout, QSizePolicy

from src.module.thirdparty.PyQt6.QWidget.QTableWidget.data import create_table_with_headers

app: QApplication = QApplication([])

table: QTableWidget = create_table_with_headers()
table.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

layout: QHBoxLayout = QHBoxLayout()
layout.addWidget(table)

window: QWidget = QWidget()
window.setLayout(layout)
window.setFixedWidth(500)
window.setFixedHeight(500)
window.show()

app.exec()
