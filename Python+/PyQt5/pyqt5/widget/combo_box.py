import sys

from PyQt5 import QtWidgets
from PyQt5.QtCore import QSize, QRect
from PyQt5.QtWidgets import QMainWindow, QWidget, QComboBox


class ComboBocWindow(QMainWindow):
    def __init__(self):
        QMainWindow.__init__(self)

        self.setMinimumSize(QSize(640, 140))
        self.setWindowTitle("Combobox example")

        central_widget = QWidget(self)
        self.setCentralWidget(central_widget)

        # Create combobox and add items.
        self.comboBox = QComboBox(central_widget)
        self.comboBox.setGeometry(QRect(40, 40, 491, 31))
        self.comboBox.setObjectName("comboBox")
        self.comboBox.addItem("PyQt")
        self.comboBox.addItem("Qt")
        self.comboBox.addItem("Python")
        self.comboBox.addItem("Example")


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    mainWin = ComboBocWindow()
    mainWin.show()
    sys.exit(app.exec_())
