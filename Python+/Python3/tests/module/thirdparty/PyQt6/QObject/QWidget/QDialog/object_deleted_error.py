from PyQt6.QtWidgets import QLabel, QPushButton, QApplication, QDialog, QVBoxLayout

from tests.module.thirdparty.PyQt6 import app


# Reproduce "RuntimeError: wrapped C/C++ object of type QLabel has been deleted"
class MyDialog(QDialog):
    def __init__(self):
        super().__init__()
        label: QLabel = QLabel("This is a dialog")

        button: QPushButton = QPushButton("Reproduce the error")
        button.clicked.connect(lambda: print(label.text()))

        layout: QVBoxLayout = QVBoxLayout()
        layout.addWidget(label)
        layout.addWidget(button)

        self.setLayout(layout)
        self.finished.connect(QApplication.quit)

        label.deleteLater()

    def show_dialog(self):
        self.show()


with app():
    dialog: MyDialog = MyDialog()
    dialog.show_dialog()