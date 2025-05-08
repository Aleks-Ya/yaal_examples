from PyQt6.QtWidgets import QPushButton

from src.module.thirdparty.PyQt6 import vbox

with vbox() as layout:
    button: QPushButton = QPushButton('Hover over me')
    button.setToolTip("Good boy")
    layout.addWidget(button)
