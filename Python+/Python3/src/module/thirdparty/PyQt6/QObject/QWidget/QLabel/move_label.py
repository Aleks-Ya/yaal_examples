from PyQt6.QtWidgets import QApplication, QLabel, QWidget

app: QApplication = QApplication([])
window: QWidget = QWidget()
window.setGeometry(200, 100, 400, 300)

label: QLabel = QLabel(window)
label.setText('Hello, PyQt6!')
label.move(50, 50)

window.show()

app.exec()
