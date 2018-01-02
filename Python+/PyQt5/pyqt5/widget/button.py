import sys

from PyQt5 import QtWidgets
from PyQt5.QtCore import QSize
from PyQt5.QtWidgets import QMainWindow
from PyQt5.QtWidgets import QPushButton


class ButtonWindow(QMainWindow):
    def __init__(self):
        QMainWindow.__init__(self)

        self.setMinimumSize(QSize(300, 200))
        self.setWindowTitle("PyQt button example")

        button = QPushButton('Click me', self)
        button.clicked.connect(self.click_method)
        button.resize(100, 32)
        button.move(50, 50)

    @staticmethod
    def click_method():
        print('Clicked Pyqt button.')


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    mainWin = ButtonWindow()
    mainWin.show()
    sys.exit(app.exec_())
