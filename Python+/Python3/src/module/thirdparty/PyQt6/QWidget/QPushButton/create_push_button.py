from PyQt6.QtWidgets import QApplication, QPushButton


app: QApplication = QApplication([])

button: QPushButton = QPushButton('Click Me')

button.show()
app.exec()
