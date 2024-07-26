from PyQt6.QtWidgets import QApplication, QLabel
from PyQt6.QtGui import QFont

app: QApplication = QApplication([])

label: QLabel = QLabel('Hello, PyQt6!')
font: QFont = QFont('Arial', 40)
label.setFont(font)

label.show()
app.exec()
