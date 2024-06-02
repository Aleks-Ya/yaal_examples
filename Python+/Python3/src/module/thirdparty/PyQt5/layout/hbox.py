import sys

from PyQt5.QtWidgets import (QWidget, QPushButton,
                             QHBoxLayout, QApplication)


class HBoxLayoutExample(QWidget):

    def __init__(self):
        super().__init__()

        self.init_ui()

    def init_ui(self):
        ok_button = QPushButton("OK")
        cancel_button = QPushButton("Cancel")

        hbox = QHBoxLayout()
        hbox.addStretch(1)
        hbox.addWidget(ok_button)
        hbox.addWidget(cancel_button)

        self.setLayout(hbox)

        self.setGeometry(300, 300, 300, 150)
        self.setWindowTitle('Buttons')
        self.show()


if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = HBoxLayoutExample()
    sys.exit(app.exec_())
