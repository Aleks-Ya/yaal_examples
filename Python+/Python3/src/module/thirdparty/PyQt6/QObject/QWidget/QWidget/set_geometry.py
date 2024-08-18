from PyQt6.QtWidgets import QApplication, QWidget

app: QApplication = QApplication([])

widget: QWidget = QWidget()
widget.setGeometry(10, 10, 600, 600)
widget.show()

app.exec()
