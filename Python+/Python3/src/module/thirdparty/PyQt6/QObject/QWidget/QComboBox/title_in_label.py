from typing import Optional

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QComboBox, QHBoxLayout, QLabel

from src.module.thirdparty.PyQt6 import window


class TitledComboBoxLayout(QHBoxLayout):
    def __init__(self, title: str, items: Optional[list[str]] = None):
        super().__init__()
        label: QLabel = QLabel(title)
        self.combo_box: QComboBox = QComboBox()
        if items:
            self.combo_box.addItems(items)
        self.setAlignment(Qt.AlignmentFlag.AlignLeft)
        self.addWidget(label)
        self.addWidget(self.combo_box)

    def set_current_text(self, current_text: str) -> None:
        self.combo_box.setCurrentText(current_text)


with window() as window:
    animals: list[str] = ["Cat", "Dog", "Rabbit"]
    layout: TitledComboBoxLayout = TitledComboBoxLayout('Choose animal:', animals)
    window.setLayout(layout)
