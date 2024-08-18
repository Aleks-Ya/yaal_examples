from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QApplication, QPushButton, QWidget, QVBoxLayout

app: QApplication = QApplication([])

icon: QIcon = QIcon('info.png')

button: QPushButton = QPushButton()
button.setIcon(icon)
button.setIconSize(button.sizeHint())
button.setFixedSize(icon.actualSize(button.iconSize()))

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(button)

window: QWidget = QWidget()
window.setLayout(layout)
window.resize(400, 300)
window.show()

app.exec()
