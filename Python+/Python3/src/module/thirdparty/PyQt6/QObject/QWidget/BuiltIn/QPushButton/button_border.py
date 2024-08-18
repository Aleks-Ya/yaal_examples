from PyQt6.QtWidgets import QApplication, QPushButton

app: QApplication = QApplication([])

button: QPushButton = QPushButton('Button without border')
button.setStyleSheet("border: none;")
button.show()

app.exec()
