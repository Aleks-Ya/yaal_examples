from typing import Optional

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QApplication, QSpinBox, QHBoxLayout, QLabel, QWidget, QVBoxLayout


class TitledSpinBoxLayout(QHBoxLayout):
    def __init__(self, title: str, minimum: Optional[int] = None, maximum: Optional[int] = None,
                 value: Optional[int] = None):
        super().__init__()
        self.label: QLabel = QLabel(title)
        self.spin_box: QSpinBox = QSpinBox()
        if minimum:
            self.spin_box.setMinimum(minimum)
        if maximum:
            self.spin_box.setMaximum(maximum)
        if value:
            self.spin_box.setValue(value)
        self.setAlignment(Qt.AlignmentFlag.AlignLeft)
        self.addWidget(self.label)
        self.addWidget(self.spin_box)

    def set_value(self, value: int):
        self.spin_box.setValue(value)

    def set_enabled(self, enabled: bool):
        self.label.setEnabled(enabled)
        self.spin_box.setEnabled(enabled)


app: QApplication = QApplication([])

window: QWidget = QWidget()
hbox: QVBoxLayout = QVBoxLayout()

age: TitledSpinBoxLayout = TitledSpinBoxLayout('Enter your age:', 18, 65, 30)
percent: TitledSpinBoxLayout = TitledSpinBoxLayout('Your percent:', 0, 100, 50)
anything: TitledSpinBoxLayout = TitledSpinBoxLayout('Anything:')
disabled: TitledSpinBoxLayout = TitledSpinBoxLayout('Disabled:')
disabled.set_enabled(False)

hbox.addLayout(age)
hbox.addLayout(percent)
hbox.addLayout(anything)
hbox.addLayout(disabled)
window.setLayout(hbox)
window.show()

app.exec()
