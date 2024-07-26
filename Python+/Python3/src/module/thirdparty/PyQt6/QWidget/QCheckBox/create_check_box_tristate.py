from PyQt6.QtWidgets import QApplication, QCheckBox

app: QApplication = QApplication([])

checkbox: QCheckBox = QCheckBox("Check me!")
checkbox.setTristate(True)

checkbox.show()
app.exec()
