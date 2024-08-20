# Window contains two text editors: one can resize only vertically
from PyQt6.QtWidgets import QVBoxLayout, QTextEdit, QSizePolicy

from src.module.thirdparty.PyQt6 import window

with window() as window:
    text_edit_small: QTextEdit = QTextEdit()
    text_edit_small.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Fixed)

    text_edit_large: QTextEdit = QTextEdit()

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(text_edit_small)
    layout.addWidget(text_edit_large)

    window.setLayout(layout)
