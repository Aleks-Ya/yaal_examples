import sys

from PyQt5 import QtWidgets
from PyQt5.QtCore import QSize
from PyQt5.QtWidgets import QMainWindow, QPlainTextEdit


class TextAreaWindow(QMainWindow):
    def __init__(self):
        QMainWindow.__init__(self)

        self.setMinimumSize(QSize(440, 240))
        self.setWindowTitle("PyQt5 Textarea example")

        # Add text field
        self.b = QPlainTextEdit(self)
        self.b.insertPlainText("You can write text here.\n")
        self.b.move(10, 10)
        self.b.resize(400, 200)


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    mainWin = TextAreaWindow()
    mainWin.show()
    sys.exit(app.exec_())
