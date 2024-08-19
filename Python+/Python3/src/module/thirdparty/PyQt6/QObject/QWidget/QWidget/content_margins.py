from PyQt6.QtWidgets import QTextEdit, QVBoxLayout

from src.module.thirdparty.PyQt6 import window

with window() as window:
    label: QTextEdit = QTextEdit()
    label.setText("Hello")

    layout: QVBoxLayout = QVBoxLayout()
    layout.setContentsMargins(100, 100, 100, 100)
    layout.addWidget(label)

    window.setLayout(layout)
