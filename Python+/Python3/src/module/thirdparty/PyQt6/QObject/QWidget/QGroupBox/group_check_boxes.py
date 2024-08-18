from PyQt6.QtWidgets import QApplication, QCheckBox, QWidget, QVBoxLayout, QGroupBox

app: QApplication = QApplication([])

checkbox1: QCheckBox = QCheckBox("Option 1")
checkbox2: QCheckBox = QCheckBox("Option 2")
checkbox3: QCheckBox = QCheckBox("Option 3")

group_layout: QVBoxLayout = QVBoxLayout()
group_layout.addWidget(checkbox1)
group_layout.addWidget(checkbox2)
group_layout.addWidget(checkbox3)

group_box: QGroupBox = QGroupBox("Options")
group_box.setLayout(group_layout)

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(group_box)

window: QWidget = QWidget()
window.setLayout(layout)
window.show()

app.exec()
