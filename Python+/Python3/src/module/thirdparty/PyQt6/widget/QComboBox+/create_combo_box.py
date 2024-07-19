from PyQt6.QtWidgets import QApplication, QComboBox

app: QApplication = QApplication([])

combo_box: QComboBox = QComboBox()

combo_box.addItem("Option 1")
combo_box.addItems(["Option 2", "Option 3"])

combo_box.show()
app.exec()
