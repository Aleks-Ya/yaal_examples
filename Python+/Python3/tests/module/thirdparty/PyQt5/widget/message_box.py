import sys

from PyQt5 import QtWidgets
from PyQt5.QtCore import QSize
from PyQt5.QtWidgets import QMainWindow
from PyQt5.QtWidgets import QMessageBox
from PyQt5.QtWidgets import QPushButton


class MessageBoxWindow(QMainWindow):
    def __init__(self):
        QMainWindow.__init__(self)

        self.setMinimumSize(QSize(300, 200))
        self.setWindowTitle("PyQt message box example")

        button = QPushButton('Show message box', self)
        button.clicked.connect(self.click_method)
        button.resize(200, 64)
        button.move(50, 50)

    def click_method(self):
        QMessageBox.about(self, "Title", "Message")


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    mainWin = MessageBoxWindow()
    mainWin.show()
    sys.exit(app.exec_())
