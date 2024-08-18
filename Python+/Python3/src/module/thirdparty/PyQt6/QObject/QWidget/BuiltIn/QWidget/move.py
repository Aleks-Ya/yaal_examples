from PyQt6.QtWidgets import QApplication, QWidget

app: QApplication = QApplication([])

widget: QWidget = QWidget()
widget.move(300, 200)
widget.show()

assert widget.parent() is None
assert widget.pos().x() == 300
assert widget.pos().y() == 200

app.exec()
