from PyQt6.QtWidgets import QGridLayout, QPushButton, QCheckBox

from src.module.thirdparty.PyQt6 import window


def __create_widget(name: str) -> QCheckBox:
    return QCheckBox(f'Check box {name}')


with window() as window:
    item00: QCheckBox = __create_widget('00')
    item01: QCheckBox = __create_widget('01')
    item02: QCheckBox = __create_widget('02')
    item03: QCheckBox = __create_widget('03')
    item10: QCheckBox = __create_widget('10')
    item20: QCheckBox = __create_widget('20')
    item30: QCheckBox = __create_widget('30')


    def increase_size(check_box: QCheckBox):
        check_box.setText(check_box.text() + " " + "a" * 30)


    button: QPushButton = QPushButton('Increase size')
    button.clicked.connect(lambda: increase_size(item03))

    layout: QGridLayout = QGridLayout()
    layout.addWidget(item00, 0, 0)
    layout.addWidget(item01, 0, 1)
    layout.addWidget(item02, 0, 2)
    layout.addWidget(item03, 0, 3)
    layout.addWidget(item10, 1, 0)
    layout.addWidget(item20, 2, 0)
    layout.addWidget(item30, 3, 0)
    layout.addWidget(button, 3, 1)

    window.setLayout(layout)
