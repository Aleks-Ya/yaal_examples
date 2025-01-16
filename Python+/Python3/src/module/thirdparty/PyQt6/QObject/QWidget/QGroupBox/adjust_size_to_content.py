from PyQt6.QtWidgets import QCheckBox, QVBoxLayout, QGroupBox, QPushButton

from src.module.thirdparty.PyQt6 import vbox

with vbox() as layout:
    checkbox1: QCheckBox = QCheckBox("Option 1")
    checkbox2: QCheckBox = QCheckBox("Option 2")
    checkbox3: QCheckBox = QCheckBox("Option 3")

    group_layout: QVBoxLayout = QVBoxLayout()
    group_layout.addWidget(checkbox1)
    group_layout.addWidget(checkbox2)
    group_layout.addWidget(checkbox3)

    group_box: QGroupBox = QGroupBox("Options")
    group_box.setLayout(group_layout)


    def make_checkbox_longer():
        checkbox1.setText("Long long long long long long long long long 1")
        checkbox2.setText("Long long long long long long long long long 2")
        checkbox3.setText("Long long long long long long long long long 3")


    button: QPushButton = QPushButton("Increase checkboxes length")
    button.clicked.connect(make_checkbox_longer)

    layout.addWidget(group_box)
    layout.addWidget(button)
