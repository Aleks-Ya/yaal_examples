from PyQt6.QtWidgets import QDialog, QVBoxLayout, QPushButton

from src.module.thirdparty.PyQt6 import app


def on_accepted():
    print("Dialog accepted")


def on_rejected():
    print("Dialog rejected")


def on_finished():
    print("Dialog closed")


with app():
    dialog: QDialog = QDialog()
    dialog.setWindowTitle("Create Dialog")
    dialog.accepted.connect(on_accepted)
    dialog.rejected.connect(on_rejected)
    dialog.finished.connect(on_finished)

    accept_button: QPushButton = QPushButton("Accept")
    accept_button.clicked.connect(dialog.accept)

    reject_button: QPushButton = QPushButton("Reject")
    reject_button.clicked.connect(dialog.reject)

    close_button: QPushButton = QPushButton("Close")
    close_button.clicked.connect(dialog.close)

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(accept_button)
    layout.addWidget(reject_button)
    layout.addWidget(close_button)

    dialog.setLayout(layout)

    dialog.show()
