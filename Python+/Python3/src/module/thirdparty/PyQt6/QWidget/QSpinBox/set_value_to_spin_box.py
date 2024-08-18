from PyQt6.QtWidgets import QApplication, QSpinBox

app: QApplication = QApplication([])

spin_box: QSpinBox = QSpinBox()
spin_box.setValue(50)
spin_box.show()

app.exec()
