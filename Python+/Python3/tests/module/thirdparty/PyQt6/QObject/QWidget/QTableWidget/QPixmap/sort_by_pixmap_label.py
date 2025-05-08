from PyQt6.QtCore import Qt
from PyQt6.QtGui import QPixmap
from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QHeaderView

from src.module.thirdparty.PyQt6 import app


class PixmapTableWidgetItem(QTableWidgetItem):
    def __init__(self, pixmap_path: str):
        super().__init__()
        self.pixmap_path: str = pixmap_path
        pixmap: QPixmap = QPixmap(pixmap_path).scaled(15, 15, Qt.AspectRatioMode.KeepAspectRatio,
                                                      Qt.TransformationMode.SmoothTransformation)
        self.setData(Qt.ItemDataRole.DecorationRole, pixmap)
        self.setFlags(self.flags() & ~Qt.ItemFlag.ItemIsEditable & ~Qt.ItemFlag.ItemIsSelectable)

    def __lt__(self, other: object):
        return self.pixmap_path < other.pixmap_path


with app():
    item_info: PixmapTableWidgetItem = PixmapTableWidgetItem("info.png")
    item_audio: PixmapTableWidgetItem = PixmapTableWidgetItem("audio.png")
    item_image: PixmapTableWidgetItem = PixmapTableWidgetItem("image.png")
    item_video: PixmapTableWidgetItem = PixmapTableWidgetItem("video.png")

    rows: int = 4
    columns: int = 2
    table: QTableWidget = QTableWidget(rows, columns)
    table.horizontalHeader().setMinimumSectionSize(0)
    table.setHorizontalHeaderLabels(["", "Name"])

    table.setItem(0, 0, item_info)
    table.setItem(1, 0, item_audio)
    table.setItem(2, 0, item_image)
    table.setItem(3, 0, item_video)

    table.setItem(0, 1, QTableWidgetItem("Info"))
    table.setItem(1, 1, QTableWidgetItem("Audio"))
    table.setItem(2, 1, QTableWidgetItem("Image"))
    table.setItem(3, 1, QTableWidgetItem("Video"))

    table.horizontalHeader().setSectionResizeMode(1, QHeaderView.ResizeMode.Stretch)
    table.resizeColumnsToContents()
    table.setSortingEnabled(True)
    table.show()
