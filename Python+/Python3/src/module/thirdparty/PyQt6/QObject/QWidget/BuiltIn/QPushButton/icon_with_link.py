from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QApplication, QPushButton

app: QApplication = QApplication([])

button: QPushButton = QPushButton()
button.setIcon(QIcon('info.png'))
button.setIconSize(button.sizeHint())
button.show()

app.exec()
