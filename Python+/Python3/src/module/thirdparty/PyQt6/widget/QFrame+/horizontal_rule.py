from PyQt6.QtWidgets import QApplication, QCheckBox, QWidget, QVBoxLayout, QFrame

app: QApplication = QApplication([])

window: QWidget = QWidget()
layout: QVBoxLayout = QVBoxLayout()

horizontal_rule: QFrame = QFrame()
horizontal_rule.setFrameShape(QFrame.Shape.HLine)
horizontal_rule.setFrameShadow(QFrame.Shadow.Sunken)

checkbox1: QCheckBox = QCheckBox("Check me 1")
checkbox2: QCheckBox = QCheckBox("Check me 2")

layout.addWidget(checkbox1)
layout.addWidget(horizontal_rule)
layout.addWidget(checkbox2)
window.setLayout(layout)

window.show()
app.exec()