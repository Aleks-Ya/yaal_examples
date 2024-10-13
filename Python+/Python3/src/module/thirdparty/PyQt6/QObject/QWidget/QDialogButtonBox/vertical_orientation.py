from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QDialogButtonBox

from src.module.thirdparty.PyQt6 import vbox

with vbox() as vbox:
    button_box: QDialogButtonBox = QDialogButtonBox(
        QDialogButtonBox.StandardButton.Ok |
        QDialogButtonBox.StandardButton.Cancel |
        QDialogButtonBox.StandardButton.RestoreDefaults)
    button_box.setOrientation(Qt.Orientation.Vertical)
    vbox.addWidget(button_box)
    vbox.parentWidget().resize(800, 600)
