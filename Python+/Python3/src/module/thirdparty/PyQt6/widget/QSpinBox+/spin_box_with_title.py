from typing import Optional

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QApplication, QSpinBox, QHBoxLayout, QLabel, QWidget, QVBoxLayout


class TitledSpinBoxLayout(QHBoxLayout):
    def __init__(self, title: str, minimum: Optional[int] = None, maximum: Optional[int] = None,
                 value: Optional[int] = None):
        super().__init__()
        label: QLabel = QLabel(title)
        self.spin_box: QSpinBox = QSpinBox()
        if minimum:
            self.spin_box.setMinimum(minimum)
        if maximum:
            self.spin_box.setMaximum(maximum)
        if value:
            self.spin_box.setValue(value)
        self.setAlignment(Qt.AlignmentFlag.AlignLeft)
        self.addWidget(label)
        self.addWidget(self.spin_box)

    def set_value(self, value: int):
        self.spin_box.setValue(value)


app: QApplication = QApplication([])

window: QWidget = QWidget()
hbox: QVBoxLayout = QVBoxLayout()

age: TitledSpinBoxLayout = TitledSpinBoxLayout('Enter your age:', 18, 65, 30)
percent: TitledSpinBoxLayout = TitledSpinBoxLayout('Your percent:', 0, 100, 50)
anything: TitledSpinBoxLayout = TitledSpinBoxLayout('Anything:')

hbox.addLayout(age)
hbox.addLayout(percent)
hbox.addLayout(anything)
window.setLayout(hbox)
window.show()

app.exec()
