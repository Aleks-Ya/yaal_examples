from PyQt6.QtWidgets import QFormLayout, QLabel, QLineEdit, QSpinBox, QPushButton, QHBoxLayout

from tests.module.thirdparty.PyQt6 import window

with window() as window:
    label: QLabel = QLabel('Label 1')
    line_edit: QLineEdit = QLineEdit()
    spin_box: QSpinBox = QSpinBox()
    button: QPushButton = QPushButton("Send form")

    name_line_edit: QLineEdit = QLineEdit()
    surname_line_edit: QLineEdit = QLineEdit()
    form_layout: QFormLayout = QFormLayout()
    form_layout.addRow("Name", name_line_edit)
    form_layout.addRow("Surname", surname_line_edit)

    city_line_edit: QLineEdit = QLineEdit('London')
    street_line_edit: QLineEdit = QLineEdit('Oxford')
    horizontal_layout: QHBoxLayout = QHBoxLayout()
    horizontal_layout.addWidget(city_line_edit)
    horizontal_layout.addWidget(street_line_edit)

    layout: QFormLayout = QFormLayout()
    layout.addRow(label, line_edit)
    layout.addRow("Label 2", spin_box)
    layout.addRow("User", form_layout)
    layout.addRow(horizontal_layout)
    layout.addRow(button)

    window.setLayout(layout)
