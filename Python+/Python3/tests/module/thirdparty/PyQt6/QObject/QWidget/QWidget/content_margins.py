from PyQt6.QtWidgets import QTextEdit

from src.module.thirdparty.PyQt6 import vbox

with vbox() as layout:
    label: QTextEdit = QTextEdit()
    label.setText("Hello")

    layout.setContentsMargins(100, 100, 100, 100)
    layout.addWidget(label)
