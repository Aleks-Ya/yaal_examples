from PyQt6.QtWidgets import QDialog, QVBoxLayout, QPushButton, QLineEdit

from module.thirdparty.PyQt6.QObject.QWidget.QDialog.modal.helper import create_modal_dialog
from tests.module.thirdparty.PyQt6 import window

with window() as window:
    dialog: QDialog = create_modal_dialog(window, is_modal=True, window_modality=None)

    button: QPushButton = QPushButton("Show dialog")
    button.clicked.connect(dialog.exec)  # Use "exec" instead of "show"

    line_edit: QLineEdit = QLineEdit("is it available to click?")

    window_layout: QVBoxLayout = QVBoxLayout()
    window_layout.addWidget(button)
    window_layout.addWidget(line_edit)

    window.setWindowTitle("The parent window")
    window.resize(640, 480)
    window.setLayout(window_layout)
    window.show()
