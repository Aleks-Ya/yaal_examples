from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QApplication, QCheckBox


def on_state_changed(state: int):
    is_checked: bool = checkbox.checkState() == Qt.CheckState.Checked
    is_partially_checked: bool = checkbox.checkState() == Qt.CheckState.PartiallyChecked
    is_unchecked: bool = checkbox.checkState() == Qt.CheckState.Unchecked
    print(f"Checkbox state: {state}, Is checked: {is_checked}, Is partially checked: {is_partially_checked}, "
          f"Is unchecked: {is_unchecked}")


app: QApplication = QApplication([])

checkbox: QCheckBox = QCheckBox("Check me!")
checkbox.setTristate(True)
checkbox.stateChanged.connect(on_state_changed)

checkbox.show()
app.exec()
