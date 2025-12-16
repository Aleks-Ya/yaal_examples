from PyQt6.QtWidgets import QWidget


class EmptyWindow(QWidget):
    def __init__(self):
        super().__init__()
        self.initialize_ui()

    def initialize_ui(self):
        self.setGeometry(200, 100, 400, 300)
        self.setWindowTitle("Empty Window")
        self.show()


from tests.module.thirdparty.PyQt6 import app

with app():
    window: EmptyWindow = EmptyWindow()
