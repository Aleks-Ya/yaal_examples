from typing import Optional

from PyQt6.QtGui import QColor
from PyQt6.QtWidgets import QApplication, QTableWidget, QTableWidgetItem, QColorDialog

app: QApplication = QApplication([])


def __open_color_dialog(row, column):
    color: Optional[QColor] = QColorDialog.getColor()
    if color.isValid():
        table.item(row, column).setBackground(color)


table: QTableWidget = QTableWidget(2, 2)
table.cellClicked.connect(__open_color_dialog)

table.setItem(0, 0, QTableWidgetItem("Item 00"))
table.setItem(0, 1, QTableWidgetItem("Item 01"))
table.setItem(0, 2, QTableWidgetItem("Item 02"))
table.setItem(1, 0, QTableWidgetItem("Item 10"))
table.setItem(1, 1, QTableWidgetItem("Item 11"))
table.setItem(1, 2, QTableWidgetItem("Item 12"))

table.show()
app.exec()
