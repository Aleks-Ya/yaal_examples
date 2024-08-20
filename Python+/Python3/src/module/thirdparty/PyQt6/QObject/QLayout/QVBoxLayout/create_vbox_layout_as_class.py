from PyQt6.QtWidgets import QVBoxLayout, QPushButton


class LayoutWithButtons(QVBoxLayout):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        button1: QPushButton = QPushButton('Button 1')
        button2: QPushButton = QPushButton('Button 2')
        self.addWidget(button1)
        self.addWidget(button2)


from src.module.thirdparty.PyQt6 import window

with window() as window:
    layout: LayoutWithButtons = LayoutWithButtons()
    window.setLayout(layout)
