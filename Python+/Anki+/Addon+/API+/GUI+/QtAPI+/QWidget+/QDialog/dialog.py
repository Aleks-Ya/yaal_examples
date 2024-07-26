from aqt import mw
from aqt.qt import QDialog, QVBoxLayout, QDialogButtonBox, QApplication


def __accept():
    print("Accept")


def __reject():
    print("Reject")


app = QApplication([])

dialog: QDialog = QDialog(mw)
layout: QVBoxLayout = QVBoxLayout(dialog)

button_box: QDialogButtonBox = QDialogButtonBox(
    QDialogButtonBox.StandardButton.Ok | QDialogButtonBox.StandardButton.Cancel)
button_box.accepted.connect(__accept)
button_box.rejected.connect(__reject)
layout.addWidget(button_box)
dialog.setLayout(layout)
dialog.exec()
