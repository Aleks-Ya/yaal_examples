from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QApplication, QTableWidget, QTableWidgetItem

app: QApplication = QApplication([])

table: QTableWidget = QTableWidget(1, 3)

item: QTableWidgetItem = QTableWidgetItem("Disabled Cell")
item.setFlags(item.flags() & ~Qt.ItemFlag.ItemIsEnabled)

table.setItem(0, 0, QTableWidgetItem("Enabled Cell"))
table.setItem(0, 1, item)
table.setItem(0, 2, QTableWidgetItem("Enabled Cell"))

table.show()

app.exec()
