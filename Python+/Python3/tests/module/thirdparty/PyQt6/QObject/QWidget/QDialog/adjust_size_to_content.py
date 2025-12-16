from PyQt6.QtWidgets import QDialog, QVBoxLayout, QLabel, QPushButton, QApplication

from tests.module.thirdparty.PyQt6 import app

with app():
    label: QLabel = QLabel("This is a dialog")

    button: QPushButton = QPushButton("Increase label length")
    button.clicked.connect(lambda: label.setText(label.text() + " " + "a" * 50))

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(label)
    layout.addWidget(button)

    dialog: QDialog = QDialog()
    dialog.finished.connect(QApplication.quit)
    dialog.setLayout(layout)
    dialog.show()
