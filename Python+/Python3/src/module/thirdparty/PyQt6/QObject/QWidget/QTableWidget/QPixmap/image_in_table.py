from PyQt6.QtCore import Qt
from PyQt6.QtGui import QPixmap
from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QLabel

from src.module.thirdparty.PyQt6 import app

with app():
    pixmap: QPixmap = QPixmap("info.png").scaled(15, 15, Qt.AspectRatioMode.KeepAspectRatio,
                                                 Qt.TransformationMode.SmoothTransformation)

    item_icon_and_text: QTableWidgetItem = QTableWidgetItem("Hello")
    item_icon_and_text.setData(Qt.ItemDataRole.DecorationRole, pixmap)
    item_icon_and_text.setFlags(
        item_icon_and_text.flags() & ~Qt.ItemFlag.ItemIsEditable & ~Qt.ItemFlag.ItemIsSelectable)

    label: QLabel = QLabel()
    label.setPixmap(pixmap)
    label.setAlignment(Qt.AlignmentFlag.AlignCenter)

    rows: int = 1
    columns: int = 3
    table: QTableWidget = QTableWidget(rows, columns)
    table.horizontalHeader().setMinimumSectionSize(0)
    table.setItem(0, 0, item_icon_and_text)
    table.setCellWidget(0, 1, label)
    table.resizeColumnsToContents()
    table.show()
