from PyQt6.QtWidgets import QDialog, QVBoxLayout, QLabel, QPushButton, QApplication

from tests.module.thirdparty.PyQt6 import app


class MyDialog(QDialog):
    def __init__(self):
        super().__init__()
        label: QLabel = QLabel("This is a dialog")

        button: QPushButton = QPushButton("Close")
        button.clicked.connect(self.close)

        layout: QVBoxLayout = QVBoxLayout()
        layout.addWidget(label)
        layout.addWidget(button)

        self.setLayout(layout)
        self.finished.connect(QApplication.quit)

    def show_dialog(self):
        self.show()


with app():
    dialog: MyDialog = MyDialog()
    dialog.show_dialog()
