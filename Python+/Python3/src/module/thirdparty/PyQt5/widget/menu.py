import sys

from PyQt5 import QtWidgets
from PyQt5.QtCore import QSize
from PyQt5.QtGui import QIcon
from PyQt5.QtWidgets import QMainWindow, QPushButton, QAction


class MenuWindow(QMainWindow):
    def __init__(self):
        QMainWindow.__init__(self)

        self.setMinimumSize(QSize(300, 100))
        self.setWindowTitle("PyQt menu example")

        # Add button widget
        button = QPushButton('Pyqt', self)
        button.clicked.connect(self.click_method)
        button.resize(100, 32)
        button.move(130, 30)
        button.setToolTip('This is a tooltip message.')

        # Create new action
        new_action = QAction(QIcon('new.png'), '&New', self)
        new_action.setShortcut('Ctrl+N')
        new_action.setStatusTip('New document')
        new_action.triggered.connect(self.new_call)

        # Create new action
        open_action = QAction(QIcon('open.png'), '&Open', self)
        open_action.setShortcut('Ctrl+O')
        open_action.setStatusTip('Open document')
        open_action.triggered.connect(self.open_call)

        # Create exit action
        exit_action = QAction(QIcon('exit.png'), '&Exit', self)
        exit_action.setShortcut('Ctrl+Q')
        exit_action.setStatusTip('Exit application')
        exit_action.triggered.connect(self.exit_call)

        # Create menu bar and add action
        menu_bar = self.menuBar()
        file_menu = menu_bar.addMenu('&File')
        file_menu.addAction(new_action)
        file_menu.addAction(open_action)
        file_menu.addAction(exit_action)

    @staticmethod
    def open_call():
        print('Open')

    @staticmethod
    def new_call():
        print('New')

    @staticmethod
    def exit_call():
        print('Exit app')

    @staticmethod
    def click_method():
        print('PyQt')


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    mainWin = MenuWindow()
    mainWin.show()
    sys.exit(app.exec_())
