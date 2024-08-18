from PyQt6.QtWidgets import QApplication, QCheckBox


def on_state_changed(state: int):
    print(f"Checkbox state: {state}, Is checked: {checkbox.isChecked()}")


app: QApplication = QApplication([])

checkbox: QCheckBox = QCheckBox("Check me!")
checkbox.stateChanged.connect(on_state_changed)
checkbox.show()

app.exec()
