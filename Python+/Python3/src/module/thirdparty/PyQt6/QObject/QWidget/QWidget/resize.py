from PyQt6.QtWidgets import QApplication, QWidget

app: QApplication = QApplication([])

widget: QWidget = QWidget()
assert widget.size().width() == 640
assert widget.size().height() == 480
widget.resize(300, 200)
assert widget.size().width() == 300
assert widget.size().height() == 200
widget.show()

app.exec()
