from PyQt6.QtWidgets import QApplication, QComboBox

app: QApplication = QApplication([])

combo_box: QComboBox = QComboBox()
combo_box.addItems(["Choose animal", "Cat", "Dog", "Rabbit"])
combo_box.model().item(0).setEnabled(False)
combo_box.show()

app.exec()
