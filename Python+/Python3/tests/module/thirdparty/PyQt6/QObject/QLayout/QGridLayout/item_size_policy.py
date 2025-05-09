from PyQt6.QtWidgets import QGridLayout, QTextEdit, QSizePolicy

from src.module.thirdparty.PyQt6 import window


def __create_widget(name: str) -> QTextEdit:
    text_edit: QTextEdit = QTextEdit(f'Information about an event {name}')
    text_edit.setSizePolicy(QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Minimum)
    return text_edit


with window() as window:
    item00: QTextEdit = __create_widget('00')
    item01: QTextEdit = __create_widget('01')
    item02: QTextEdit = __create_widget('02')
    item03: QTextEdit = __create_widget('03')
    item10: QTextEdit = __create_widget('10')
    item20: QTextEdit = __create_widget('20')
    item30: QTextEdit = __create_widget('30')

    item20.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)

    layout: QGridLayout = QGridLayout()
    layout.addWidget(item00, 0, 0)
    layout.addWidget(item01, 0, 1)
    layout.addWidget(item02, 0, 2)
    layout.addWidget(item03, 0, 3)
    layout.addWidget(item10, 1, 0)
    layout.addWidget(item20, 2, 0)
    layout.addWidget(item30, 3, 0)

    window.setLayout(layout)
    window.resize(500, 300)
