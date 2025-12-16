from PyQt6.QtWidgets import QLineEdit, QHBoxLayout, QLabel

from tests.module.thirdparty.PyQt6 import vbox


class TitledLineEditLayout(QHBoxLayout):
    def __init__(self, title: str, text: str = None, placeholder: str = None, clear_button_enabled: bool = False):
        super().__init__()
        label: QLabel = QLabel(title)
        self.__line_edit: QLineEdit = QLineEdit(text)
        self.__line_edit.setPlaceholderText(placeholder)
        self.__line_edit.setClearButtonEnabled(clear_button_enabled)
        self.addWidget(label)
        self.addWidget(self.__line_edit)

    def get_text(self) -> str:
        return self.__line_edit.text()


with vbox() as vbox:
    vbox.addLayout(TitledLineEditLayout('Your name:'))
    layout2: TitledLineEditLayout = TitledLineEditLayout('Your name:', 'John Dow')
    assert layout2.get_text() == 'John Dow'
    vbox.addLayout(layout2)
    vbox.addLayout(TitledLineEditLayout('Your name:', placeholder='Print your name here'))
    vbox.addLayout(TitledLineEditLayout('Your name:', text="John", clear_button_enabled=True))
