from PyQt6.QtWidgets import QApplication, QTableWidget, QWidget, QHBoxLayout, QSizePolicy

from src.module.thirdparty.PyQt6.QWidget.QTableWidget.data import create_table_with_headers

app: QApplication = QApplication([])

window: QWidget = QWidget()
layout: QHBoxLayout = QHBoxLayout()

table: QTableWidget = create_table_with_headers()

table.setSizePolicy(QSizePolicy.Policy.MinimumExpanding, QSizePolicy.Policy.MinimumExpanding)

layout.addWidget(table)

window.setLayout(layout)
window.setFixedWidth(500)
window.setFixedHeight(500)

window.show()
app.exec()
