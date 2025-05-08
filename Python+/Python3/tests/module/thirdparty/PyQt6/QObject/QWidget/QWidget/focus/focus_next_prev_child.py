from typing import Optional

from PyQt6.QtWidgets import QPushButton

from src.module.thirdparty.PyQt6 import vbox


class TabOrderedButton(QPushButton):
    def __init__(self, text: str):
        super().__init__(text=text)
        self.__previous_child: Optional[QPushButton] = None
        self.__next_child: Optional[QPushButton] = None

    def set_previous_child(self, child: Optional[QPushButton]):
        self.__previous_child = child

    def set_next_child(self, child: Optional[QPushButton]):
        self.__next_child = child

    def focusNextPrevChild(self, next: bool) -> bool:
        if next:
            if self.__next_child:
                self.__next_child.setFocus()
                return True
        else:
            if self.__previous_child:
                self.__previous_child.setFocus()
                return True
        return False


with vbox() as layout:
    layout.parent().setStyleSheet("QPushButton:focus { border: 2px solid blue; }")

    button1: TabOrderedButton = TabOrderedButton('Button 2nd')
    button1.clicked.connect(lambda: print(f'{button1.text()} clicked'))

    button2: TabOrderedButton = TabOrderedButton('Button 3nd')
    button2.clicked.connect(lambda: print(f'{button2.text()} clicked'))

    button3: TabOrderedButton = TabOrderedButton('Button 1st')
    button3.clicked.connect(lambda: print(f'{button3.text()} clicked'))

    button1.set_previous_child(button3)
    button1.set_next_child(button2)

    button2.set_previous_child(button1)
    button2.set_next_child(button3)

    button3.set_previous_child(button2)
    button3.set_next_child(button1)

    layout.addWidget(button1)
    layout.addWidget(button2)
    layout.addWidget(button3)

    button3.setFocus()
