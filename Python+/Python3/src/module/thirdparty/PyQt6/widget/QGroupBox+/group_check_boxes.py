from PyQt6.QtWidgets import QApplication, QCheckBox, QWidget, QVBoxLayout, QGroupBox

app: QApplication = QApplication([])

window: QWidget = QWidget()
layout: QVBoxLayout = QVBoxLayout()

group_box: QGroupBox = QGroupBox("Options")
group_layout: QVBoxLayout = QVBoxLayout()

checkbox1: QCheckBox = QCheckBox("Option 1")
checkbox2: QCheckBox = QCheckBox("Option 2")
checkbox3: QCheckBox = QCheckBox("Option 3")

group_layout.addWidget(checkbox1)
group_layout.addWidget(checkbox2)
group_layout.addWidget(checkbox3)

group_box.setLayout(group_layout)
layout.addWidget(group_box)

window.setLayout(layout)
window.show()

app.exec()