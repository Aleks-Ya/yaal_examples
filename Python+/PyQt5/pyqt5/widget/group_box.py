import sys

from PyQt5.QtWidgets import (QApplication, QGridLayout, QGroupBox,
                             QRadioButton, QVBoxLayout, QWidget)


class GroupBoxWindow(QWidget):
    def __init__(self, parent=None):
        super(GroupBoxWindow, self).__init__(parent)

        grid = QGridLayout()
        grid.addWidget(self.create_example_group(), 0, 0)
        grid.addWidget(self.create_example_group(), 1, 0)
        grid.addWidget(self.create_example_group(), 0, 1)
        grid.addWidget(self.create_example_group(), 1, 1)
        self.setLayout(grid)

        self.setWindowTitle("PyQt5 Group Box")
        self.resize(400, 300)

    @staticmethod
    def create_example_group():
        group_box = QGroupBox("Best Food")

        radio1 = QRadioButton("&Radio pizza")
        radio2 = QRadioButton("R&adio taco")
        radio3 = QRadioButton("Ra&dio burrito")

        radio1.setChecked(True)

        vbox = QVBoxLayout()
        vbox.addWidget(radio1)
        vbox.addWidget(radio2)
        vbox.addWidget(radio3)
        vbox.addStretch(1)
        group_box.setLayout(vbox)

        return group_box


if __name__ == '__main__':
    app = QApplication(sys.argv)
    clock = GroupBoxWindow()
    clock.show()
    sys.exit(app.exec_())
