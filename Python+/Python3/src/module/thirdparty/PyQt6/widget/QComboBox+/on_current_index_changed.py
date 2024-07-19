from PyQt6.QtWidgets import QApplication, QComboBox


def on_combobox_changed(index):
    print(f"Selected index: {index}")


app: QApplication = QApplication([])

combo_box: QComboBox = QComboBox()
combo_box.addItems(["Option 1", "Option 2", "Option 3"])
combo_box.currentIndexChanged.connect(on_combobox_changed)

combo_box.show()
app.exec()
