from PyQt6.QtWidgets import QPushButton


class InfoButton(QPushButton):
    def __init__(self, text: str):
        super().__init__()
        self.setText(text)
        self.setFixedWidth(20)
        self.setToolTip("Click to get lucky")
        self.setStyleSheet("border: none;")
        self.clicked.connect(self.__on_click)
        self.__clicked: bool = False

    def is_clicked(self):
        return self.__clicked

    def __on_click(self) -> None:
        self.__clicked: bool = True
