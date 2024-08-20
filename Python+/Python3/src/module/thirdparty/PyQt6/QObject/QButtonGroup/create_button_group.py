from PyQt6.QtWidgets import QButtonGroup, QVBoxLayout, QCheckBox

from src.module.thirdparty.PyQt6 import window

with window() as window:
    check_box_1: QCheckBox = QCheckBox('Option 1')
    check_box_2: QCheckBox = QCheckBox('Option 2')
    check_box_3: QCheckBox = QCheckBox('Option 3')

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(check_box_1)
    layout.addWidget(check_box_2)
    layout.addWidget(check_box_3)

    button_group: QButtonGroup = QButtonGroup()
    button_group.addButton(check_box_1)
    button_group.addButton(check_box_2)
    button_group.addButton(check_box_3)

    window.setLayout(layout)
