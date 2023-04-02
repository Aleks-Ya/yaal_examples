import sys

from PyQt6 import QtCore
from PyQt6.QtWidgets import QApplication, QLabel, QMainWindow


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        label = QLabel("Hello, World!")
        label.setAlignment(QtCore.Qt.AlignmentFlag.AlignCenter)
        self.setCentralWidget(label)


def main():
    app = QApplication(sys.argv)
    main_window = MainWindow()
    main_window.show()
    sys.exit(app.exec())


if __name__ == "__main__":
    main()
