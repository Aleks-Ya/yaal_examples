from PyQt6.QtWidgets import QApplication, QTableWidget, QWidget, QHBoxLayout

from src.module.thirdparty.PyQt6.QObject.QWidget.BuiltIn.QTableWidget.data import create_table_with_headers

app: QApplication = QApplication([])

table: QTableWidget = create_table_with_headers()
table.setMinimumSize(table.sizeHint())

layout: QHBoxLayout = QHBoxLayout()
layout.addWidget(table)

window: QWidget = QWidget()
window.setLayout(layout)
window.adjustSize()

window.show()
app.exec()
