from PyQt6.QtWidgets import QApplication, QSpinBox

app: QApplication = QApplication([])

spin_box: QSpinBox = QSpinBox()
spin_box.setMinimum(0)
spin_box.setMaximum(10)
spin_box.show()

app.exec()
