from PyQt6.QtWidgets import QDialogButtonBox, QPushButton

from src.module.thirdparty.PyQt6 import app


def accept():
    print("Accept")


def reject():
    print("Reject")


def restore_defaults():
    print("Restore Defaults")


with app():
    button_box: QDialogButtonBox = QDialogButtonBox(
        QDialogButtonBox.StandardButton.Ok |
        QDialogButtonBox.StandardButton.Cancel |
        QDialogButtonBox.StandardButton.RestoreDefaults)
    button_box.accepted.connect(accept)
    button_box.rejected.connect(reject)
    restore_defaults_button: QPushButton = button_box.button(QDialogButtonBox.StandardButton.RestoreDefaults)
    restore_defaults_button.clicked.connect(restore_defaults)
    button_box.show()
