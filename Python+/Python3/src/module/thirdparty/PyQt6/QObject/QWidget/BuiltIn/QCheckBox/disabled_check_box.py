from PyQt6.QtWidgets import QApplication, QCheckBox, QWidget, QVBoxLayout

app: QApplication = QApplication([])

enabled_checkbox: QCheckBox = QCheckBox("Enabled")
disabled_checkbox: QCheckBox = QCheckBox("Disabled")
disabled_checkbox.setEnabled(False)

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(enabled_checkbox)
layout.addWidget(disabled_checkbox)

window: QWidget = QWidget()
window.setLayout(layout)
window.show()

app.exec()
