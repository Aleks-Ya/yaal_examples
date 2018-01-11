import sys

from PyQt5 import QtWidgets
from PyQt5.QtCore import QSize
from PyQt5.QtWidgets import QMainWindow, QLabel, QLineEdit
from PyQt5.QtWidgets import QPushButton


class LineEditWindow(QMainWindow):
    def __init__(self):
        QMainWindow.__init__(self)

        self.setMinimumSize(QSize(320, 140))
        self.setWindowTitle("PyQt Line Edit example")

        self.nameLabel = QLabel(self)
        self.nameLabel.setText('Name:')
        self.line = QLineEdit(self)

        self.line.move(80, 20)
        self.line.resize(200, 32)
        self.nameLabel.move(20, 20)

        button = QPushButton('OK', self)
        button.clicked.connect(self.click_method)
        button.resize(200, 32)
        button.move(80, 60)

    def click_method(self):
        print('Your name: ' + self.line.text())


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    mainWin = LineEditWindow()
    mainWin.show()
    sys.exit(app.exec_())
