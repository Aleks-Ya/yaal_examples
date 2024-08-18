from PyQt6.QtWidgets import QApplication, QDialogButtonBox, QDialog, QVBoxLayout

app: QApplication = QApplication([])


def accept():
    print("Accept")


def reject():
    print("Reject")


dialog: QDialog = QDialog()

button_box: QDialogButtonBox = QDialogButtonBox(
    QDialogButtonBox.StandardButton.Ok | QDialogButtonBox.StandardButton.Cancel)
button_box.accepted.connect(dialog.accept)
button_box.rejected.connect(dialog.reject)

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(button_box)

dialog.setLayout(layout)
dialog.show()

app.exec()
