from PyQt6.QtWidgets import QCheckBox, QVBoxLayout

from src.module.thirdparty.PyQt6 import window

with window() as window:
    enabled_checkbox: QCheckBox = QCheckBox("Enabled")
    disabled_checkbox: QCheckBox = QCheckBox("Disabled")
    disabled_checkbox.setEnabled(False)

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(enabled_checkbox)
    layout.addWidget(disabled_checkbox)

    window.setLayout(layout)
