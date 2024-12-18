from PyQt6.QtWidgets import QDialog, QVBoxLayout, QLabel, QPushButton, QApplication

from src.module.thirdparty.PyQt6 import app

with app():
    dialog: QDialog = QDialog()
    dialog.setWindowTitle("Create Dialog")

    label: QLabel = QLabel("This is a dialog")

    button: QPushButton = QPushButton("Close")
    # noinspection PyUnresolvedReferences
    button.clicked.connect(dialog.close)

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(label)
    layout.addWidget(button)

    dialog.setLayout(layout)
    # noinspection PyUnresolvedReferences
    dialog.finished.connect(QApplication.quit)

    dialog.show()
