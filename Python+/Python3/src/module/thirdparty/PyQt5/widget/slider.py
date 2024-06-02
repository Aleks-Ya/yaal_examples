import sys

from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import (QApplication, QGridLayout, QGroupBox,
                             QRadioButton, QVBoxLayout, QWidget, QSlider)


class SliderWindow(QWidget):
    def __init__(self, parent=None):
        super(SliderWindow, self).__init__(parent)

        grid = QGridLayout()
        grid.addWidget(self.create_example_group(), 0, 0)
        grid.addWidget(self.create_example_group(), 1, 0)
        grid.addWidget(self.create_example_group(), 0, 1)
        grid.addWidget(self.create_example_group(), 1, 1)
        self.setLayout(grid)

        self.setWindowTitle("PyQt5 Sliders")
        self.resize(400, 300)

    @staticmethod
    def create_example_group():
        group_box = QGroupBox("Slider Example")

        radio1 = QRadioButton("&Radio horizontal slider")

        slider = QSlider(Qt.Horizontal)
        slider.setFocusPolicy(Qt.StrongFocus)
        slider.setTickPosition(QSlider.TicksBothSides)
        slider.setTickInterval(10)
        slider.setSingleStep(1)

        radio1.setChecked(True)

        vbox = QVBoxLayout()
        vbox.addWidget(radio1)
        vbox.addWidget(slider)
        vbox.addStretch(1)
        group_box.setLayout(vbox)

        return group_box


if __name__ == '__main__':
    app = QApplication(sys.argv)
    clock = SliderWindow()
    clock.show()
    sys.exit(app.exec_())
