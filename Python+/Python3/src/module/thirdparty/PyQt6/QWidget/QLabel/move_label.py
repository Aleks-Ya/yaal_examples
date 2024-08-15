import sys

from PyQt6.QtWidgets import QApplication, QLabel, QWidget


class MainWindow(QWidget):
    def __init__(self):
        super().__init__()
        self.initialize_ui()

    def initialize_ui(self):
        self.setGeometry(200, 100, 400, 300)
        label: QLabel = QLabel(self)
        label.setText('Hello, PyQt6!')
        label.move(50, 50)
        self.show()


if __name__ == '__main__':
    app: QApplication = QApplication(sys.argv)
    window: MainWindow = MainWindow()
    sys.exit(app.exec())
