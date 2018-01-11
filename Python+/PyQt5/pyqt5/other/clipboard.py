import sys

from PyQt5 import QtWidgets
from PyQt5.Qt import QApplication
from PyQt5.QtCore import QSize
from PyQt5.QtWidgets import QMainWindow, QPlainTextEdit


class ClipboardWindow(QMainWindow):
    def __init__(self):
        QMainWindow.__init__(self)

        self.setMinimumSize(QSize(440, 240))
        self.setWindowTitle("PyQt5 Clipboard example")

        # Add text field
        self.b = QPlainTextEdit(self)
        self.b.insertPlainText(
            "Use your mouse to copy text to the clipboard.\nText can be copied from any application.\n")
        self.b.move(10, 10)
        self.b.resize(400, 200)

        QApplication.clipboard().dataChanged.connect(self.clipboard_changed)

    # Get the system clipboard contents
    def clipboard_changed(self):
        text = QApplication.clipboard().text()
        print(text)
        self.b.insertPlainText(text + '\n')


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    mainWin = ClipboardWindow()
    mainWin.show()
    sys.exit(app.exec_())
