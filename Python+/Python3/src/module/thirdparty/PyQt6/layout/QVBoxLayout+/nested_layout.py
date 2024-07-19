from PyQt6.QtWidgets import QApplication, QWidget, QVBoxLayout, QPushButton, QHBoxLayout

app: QApplication = QApplication([])

window: QWidget = QWidget()
outer_layout: QVBoxLayout = QVBoxLayout()
nested_layout_1: QVBoxLayout = QVBoxLayout()
nested_layout_2: QHBoxLayout = QHBoxLayout()

button1: QPushButton = QPushButton('Button 1')
button2: QPushButton = QPushButton('Button 2')
button3: QPushButton = QPushButton('Button 3')
button4: QPushButton = QPushButton('Button 4')

nested_layout_1.addWidget(button1)
nested_layout_1.addWidget(button2)
nested_layout_2.addWidget(button3)
nested_layout_2.addWidget(button4)

outer_layout.addLayout(nested_layout_1)
outer_layout.addLayout(nested_layout_2)

window.setLayout(outer_layout)
window.show()

app.exec()
