from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QApplication, QTableWidget, QTableWidgetItem

app: QApplication = QApplication([])

rows: int = 2
columns: int = 3
table: QTableWidget = QTableWidget(rows, columns)

icon: QIcon = QIcon("info.png")

item_icon_and_text: QTableWidgetItem = QTableWidgetItem("Hello")
item_icon_and_text.setIcon(icon)
item_icon_and_text.setFlags(item_icon_and_text.flags() & ~Qt.ItemFlag.ItemIsEditable & ~Qt.ItemFlag.ItemIsSelectable)

item_icon_only: QTableWidgetItem = QTableWidgetItem("")
item_icon_only.setIcon(icon)
item_icon_only.setFlags(item_icon_only.flags() & ~Qt.ItemFlag.ItemIsEditable & ~Qt.ItemFlag.ItemIsSelectable)

table.setItem(0, 0, item_icon_and_text)
table.setItem(0, 1, item_icon_only)
table.resizeColumnsToContents()

table.show()
app.exec()
