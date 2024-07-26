from PyQt6.QtWidgets import QApplication, QPushButton, QVBoxLayout, QWidget

app: QApplication = QApplication([])
window: QWidget = QWidget()
layout: QVBoxLayout = QVBoxLayout()

button: QPushButton = QPushButton('Hover over me')
button.setToolTip("Good boy")

layout.addWidget(button)
window.setLayout(layout)
window.show()
app.exec()
