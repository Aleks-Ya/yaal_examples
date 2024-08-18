from PyQt6.QtWidgets import QApplication, QDoubleSpinBox

app: QApplication = QApplication([])

spin_box: QDoubleSpinBox = QDoubleSpinBox()
spin_box.setPrefix("$")
spin_box.setDecimals(4)
spin_box.setMinimum(1.5)
spin_box.setMaximum(7.6)
spin_box.setSingleStep(0.01)
spin_box.show()

app.exec()
