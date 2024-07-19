from PyQt6.QtWidgets import QApplication, QLabel

app: QApplication = QApplication([])

label: QLabel = QLabel('Hello, PyQt6!')

label.show()
app.exec()
