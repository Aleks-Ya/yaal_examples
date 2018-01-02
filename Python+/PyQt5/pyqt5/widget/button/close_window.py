import sys

from PyQt5 import QtWidgets
from PyQt5.QtWidgets import QMainWindow, QVBoxLayout, QWidget
from PyQt5.QtWidgets import QPushButton


class ButtonWindow(QMainWindow):
    def __init__(self):
        QMainWindow.__init__(self)

        button1 = QPushButton('Close 1')
        button1.clicked.connect(self.click_method)

        button2 = QPushButton('Close 2')
        button2.clicked.connect(self.close)

        layout = QVBoxLayout()
        layout.addWidget(button1)
        layout.addWidget(button2)

        central_widget = QWidget(self)
        central_widget.setLayout(layout)

        self.setCentralWidget(central_widget)

    def click_method(self):
        self.close()


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    mainWin = ButtonWindow()
    mainWin.show()
    sys.exit(app.exec_())
