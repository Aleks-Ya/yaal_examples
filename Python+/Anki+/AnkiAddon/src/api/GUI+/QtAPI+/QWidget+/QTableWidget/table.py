from aqt import mw
from aqt.qt import QDialog, QVBoxLayout, QApplication, QTableWidget, QTableWidgetItem

app = QApplication([])

dialog: QDialog = QDialog(mw)
layout: QVBoxLayout = QVBoxLayout(dialog)

rows: int = 3
columns: int = 3
table: QTableWidget = QTableWidget(rows, columns)

table.setItem(0, 0, QTableWidgetItem("Item 1"))
table.setItem(0, 1, QTableWidgetItem("Item 2"))
table.setItem(0, 2, QTableWidgetItem("Item 3"))

layout.addWidget(table)
dialog.setLayout(layout)
dialog.exec()
