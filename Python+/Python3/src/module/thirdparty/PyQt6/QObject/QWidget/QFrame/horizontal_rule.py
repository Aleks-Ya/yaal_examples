from PyQt6.QtWidgets import QCheckBox, QVBoxLayout, QFrame

from src.module.thirdparty.PyQt6 import window

with window() as window:
    horizontal_rule: QFrame = QFrame()
    horizontal_rule.setFrameShape(QFrame.Shape.HLine)
    horizontal_rule.setFrameShadow(QFrame.Shadow.Sunken)

    checkbox1: QCheckBox = QCheckBox("Check me 1")
    checkbox2: QCheckBox = QCheckBox("Check me 2")

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(checkbox1)
    layout.addWidget(horizontal_rule)
    layout.addWidget(checkbox2)

    window.setLayout(layout)
