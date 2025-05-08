from PyQt6.QtWidgets import QDialog, QVBoxLayout, QLabel, QPushButton, QApplication, QWidget

from src.module.thirdparty.PyQt6 import window


def __create_dialog(parent: QWidget) -> QDialog:
    dialog: QDialog = QDialog(parent)
    dialog.resize(350, 150)
    dialog.setWindowTitle("The dialog window")
    assert not dialog.isModal()
    dialog.setModal(True)

    label: QLabel = QLabel("This is a MODAL dialog")

    button: QPushButton = QPushButton("Close")
    button.clicked.connect(dialog.close)

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(label)
    layout.addWidget(button)

    dialog.setLayout(layout)
    dialog.finished.connect(QApplication.quit)
    return dialog


with window() as window:
    dialog: QDialog = __create_dialog(window)

    button: QPushButton = QPushButton("Show dialog")
    button.clicked.connect(dialog.show)

    window_layout: QVBoxLayout = QVBoxLayout()
    window_layout.addWidget(button)

    window.setWindowTitle("The parent window")
    window.resize(640, 480)
    window.setLayout(window_layout)
    window.show()
