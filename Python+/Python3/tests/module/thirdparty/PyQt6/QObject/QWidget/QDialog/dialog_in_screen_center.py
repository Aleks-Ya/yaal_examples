from PyQt6.QtCore import QRect, QPoint
from PyQt6.QtWidgets import QDialog, QVBoxLayout, QLabel, QPushButton, QApplication

from src.module.thirdparty.PyQt6 import app

# DOES NOT WORK

with app():
    dialog: QDialog = QDialog()
    dialog.setWindowTitle("Create Dialog")

    label: QLabel = QLabel("This is a dialog")

    button: QPushButton = QPushButton("Close")
    button.clicked.connect(dialog.close)

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(label)
    layout.addWidget(button)

    dialog.setLayout(layout)
    dialog.finished.connect(QApplication.quit)

    # Method 1
    screen_geometry: QRect = QApplication.primaryScreen().geometry()
    dialog_geometry: QRect = dialog.geometry()
    x: int = (screen_geometry.width() - dialog_geometry.width()) // 2
    y: int = (screen_geometry.height() - dialog_geometry.height()) // 2
    dialog.move(x, y)

    # Method 2
    qr: QRect = dialog.frameGeometry()
    cp: QPoint = QApplication.primaryScreen().availableGeometry().center()
    qr.moveCenter(cp)
    dialog.move(qr.topLeft())

    dialog.show()
