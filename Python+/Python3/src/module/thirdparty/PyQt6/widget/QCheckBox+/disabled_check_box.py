from PyQt6.QtWidgets import QApplication, QCheckBox, QWidget, QVBoxLayout

app: QApplication = QApplication([])

window: QWidget = QWidget()
layout: QVBoxLayout = QVBoxLayout()

enabled_checkbox: QCheckBox = QCheckBox("Enabled")
disabled_checkbox: QCheckBox = QCheckBox("Disabled")
disabled_checkbox.setEnabled(False)

layout.addWidget(enabled_checkbox)
layout.addWidget(disabled_checkbox)
window.setLayout(layout)

window.show()
app.exec()
