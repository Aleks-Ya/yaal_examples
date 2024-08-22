from PyQt6.QtCore import Qt
from PyQt6.QtGui import QPixmap
from PyQt6.QtWidgets import QLabel

from src.module.thirdparty.PyQt6 import window

with window() as window:
    window.setFixedSize(400, 300)
    pixmap_size: int = 30
    pixmap: QPixmap = QPixmap("info.png").scaled(pixmap_size, pixmap_size, Qt.AspectRatioMode.KeepAspectRatio,
                                                 Qt.TransformationMode.SmoothTransformation)
    label: QLabel = QLabel(window)
    label.setStyleSheet("border: 1px solid black;")
    label.setText('TEXT IS NOT DISPLAYED')
    label.setPixmap(pixmap)
    margin: int = 5
    label.setContentsMargins(margin, margin, margin, margin)
