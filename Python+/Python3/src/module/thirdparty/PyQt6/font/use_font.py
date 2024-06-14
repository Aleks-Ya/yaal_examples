from PyQt6.QtWidgets import QApplication, QLabel
from PyQt6.QtGui import QFont

app = QApplication([])

label = QLabel('Hello, PyQt6!')
font = QFont('Arial', 40)
label.setFont(font)

label.show()
app.exec()