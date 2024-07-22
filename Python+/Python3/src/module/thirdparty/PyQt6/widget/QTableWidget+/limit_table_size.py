from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QApplication, QTableWidget, QTableWidgetItem, QWidget, QHBoxLayout

app: QApplication = QApplication([])

window: QWidget = QWidget()
layout: QHBoxLayout = QHBoxLayout()

table: QTableWidget = QTableWidget(3, 3)
table.setHorizontalHeaderLabels(["Name", "Age", "City"])

table.setItem(0, 0, QTableWidgetItem("John"))
table.setItem(0, 1, QTableWidgetItem("40"))
table.setItem(0, 2, QTableWidgetItem("London"))

table.setItem(1, 0, QTableWidgetItem("Mary"))
table.setItem(1, 1, QTableWidgetItem("30"))
table.setItem(1, 2, QTableWidgetItem("Berlin"))

table.setItem(2, 0, QTableWidgetItem("Mark"))
table.setItem(2, 1, QTableWidgetItem("20"))
table.setItem(2, 2, QTableWidgetItem("Paris"))

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
