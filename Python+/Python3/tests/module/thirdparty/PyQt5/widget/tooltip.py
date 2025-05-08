import sys

from PyQt5 import QtWidgets
from PyQt5.QtCore import QSize
from PyQt5.QtWidgets import QMainWindow, QPushButton


class ToolTipWindow(QMainWindow):
    def __init__(self):
        QMainWindow.__init__(self)

        self.setMinimumSize(QSize(300, 100))
        self.setWindowTitle("PyQt tooltip example - pythonprogramminglanguage.com")

        pybutton = QPushButton('Pyqt', self)
        pybutton.clicked.connect(self.click_method)
        pybutton.resize(100, 32)
        pybutton.move(50, 20)
        pybutton.setToolTip('This is a tooltip message.')

    @staticmethod
    def click_method():
        print('PyQt')


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    mainWin = ToolTipWindow()
    mainWin.show()
    sys.exit(app.exec_())
