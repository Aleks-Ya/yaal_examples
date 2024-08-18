from PyQt6.QtCore import QObject
from PyQt6.QtWidgets import QApplication, QPushButton

app: QApplication = QApplication([])

button: QPushButton = QPushButton('Quit application')
button.clicked.connect(app.quit)

children: list[QObject] = app.children()
print(children)

button.show()
app.exec()
