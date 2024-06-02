import sys

from PyQt5.QtWidgets import (QWidget, QPushButton,
                             QVBoxLayout, QApplication)


class VBoxLayoutExample(QWidget):

    def __init__(self):
        super().__init__()

        self.init_ui()

    def init_ui(self):
        ok_button = QPushButton("OK")
        cancel_button = QPushButton("Cancel")

        vbox = QVBoxLayout()
        vbox.addStretch(1)
        vbox.addWidget(ok_button)
        vbox.addWidget(cancel_button)

        self.setLayout(vbox)

        self.setGeometry(300, 300, 300, 150)
        self.setWindowTitle('Buttons')
        self.show()


if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = VBoxLayoutExample()
    sys.exit(app.exec_())
