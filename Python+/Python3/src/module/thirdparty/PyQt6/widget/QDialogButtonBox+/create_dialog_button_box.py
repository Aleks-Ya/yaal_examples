from PyQt6.QtWidgets import QApplication, QDialogButtonBox

app: QApplication = QApplication([])


def accept():
    print("Accept")


def reject():
    print("Reject")


button_box: QDialogButtonBox = QDialogButtonBox(
    QDialogButtonBox.StandardButton.Ok | QDialogButtonBox.StandardButton.Cancel)
button_box.accepted.connect(accept)
button_box.rejected.connect(reject)

button_box.show()
app.exec()
