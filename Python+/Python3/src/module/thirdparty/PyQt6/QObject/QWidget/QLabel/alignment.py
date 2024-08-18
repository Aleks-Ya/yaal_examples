from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QApplication, QLabel

app: QApplication = QApplication([])

label: QLabel = QLabel()
label.setFixedSize(400, 300)
label.setAlignment(Qt.AlignmentFlag.AlignCenter)
label.show()

app.exec()
