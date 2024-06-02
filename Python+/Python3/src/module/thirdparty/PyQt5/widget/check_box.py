import sys

from PyQt5 import QtCore, QtWidgets
from PyQt5.QtCore import QSize
from PyQt5.QtWidgets import QMainWindow, QCheckBox


class CheckBoxWindow(QMainWindow):
    def __init__(self):
        QMainWindow.__init__(self)

        self.setMinimumSize(QSize(140, 40))
        self.setWindowTitle("Checkbox")

        self.b = QCheckBox("Awesome?", self)
        self.b.stateChanged.connect(self.click_box)
        self.b.move(20, 20)
        self.b.resize(320, 40)

    @staticmethod
    def click_box(state):
        if state == QtCore.Qt.Checked:
            print('Checked')
        else:
            print('Unchecked')


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    mainWin = CheckBoxWindow()
    mainWin.show()
    sys.exit(app.exec_())
