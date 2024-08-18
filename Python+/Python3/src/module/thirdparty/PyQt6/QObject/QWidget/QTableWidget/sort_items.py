from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QApplication, QTableWidget, QTableWidgetItem

app: QApplication = QApplication([])

rows: int = 3
columns: int = 3

table: QTableWidget = QTableWidget(rows, columns)
table.setItem(0, 0, QTableWidgetItem("Item 1"))
table.setItem(1, 0, QTableWidgetItem("Item 2"))
table.setItem(2, 0, QTableWidgetItem("Item 3"))
table.sortItems(0, Qt.SortOrder.DescendingOrder)
table.show()

app.exec()
