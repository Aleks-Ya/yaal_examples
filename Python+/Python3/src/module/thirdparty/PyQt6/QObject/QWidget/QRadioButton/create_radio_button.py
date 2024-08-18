from PyQt6.QtWidgets import QApplication, QRadioButton

app: QApplication = QApplication([])

checkbox: QRadioButton = QRadioButton("Check me!")
checkbox.show()

app.exec()
