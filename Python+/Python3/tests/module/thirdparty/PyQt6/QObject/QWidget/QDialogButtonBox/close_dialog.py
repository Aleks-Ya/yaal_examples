from PyQt6.QtWidgets import QDialogButtonBox, QDialog, QVBoxLayout

from tests.module.thirdparty.PyQt6 import app


def accept():
    print("Accept")


def reject():
    print("Reject")


with app():
    dialog: QDialog = QDialog()

    button_box: QDialogButtonBox = QDialogButtonBox(
        QDialogButtonBox.StandardButton.Ok | QDialogButtonBox.StandardButton.Cancel)
    button_box.accepted.connect(dialog.accept)
    button_box.rejected.connect(dialog.reject)

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(button_box)

    dialog.setLayout(layout)
    dialog.show()
