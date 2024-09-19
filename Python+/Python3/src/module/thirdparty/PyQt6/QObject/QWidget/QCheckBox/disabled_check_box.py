from PyQt6.QtWidgets import QCheckBox

from src.module.thirdparty.PyQt6 import vbox

with vbox() as layout:
    enabled_checkbox: QCheckBox = QCheckBox("Enabled")
    disabled_checkbox: QCheckBox = QCheckBox("Disabled")
    disabled_checkbox.setEnabled(False)

    layout.addWidget(enabled_checkbox)
    layout.addWidget(disabled_checkbox)
