from PyQt6.QtCore import Qt
from PyQt6.QtGui import QPixmap
from PyQt6.QtWidgets import QLabel

from src.module.thirdparty.PyQt6 import window

with window() as window:
    window.setGeometry(200, 100, 400, 300)
    pixmap: QPixmap = QPixmap("info.png").scaled(30, 30, Qt.AspectRatioMode.KeepAspectRatio,
                                                 Qt.TransformationMode.SmoothTransformation)
    label: QLabel = QLabel(window)
    label.setText('TEXT IS NOT DISPLAYED')
    label.setPixmap(pixmap)
