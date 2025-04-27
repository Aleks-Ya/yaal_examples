from PyQt6.QtWidgets import QPushButton

from application.calculator.calculator import summarize


class InfoButton(QPushButton):
    def __init__(self, text: str):
        super().__init__()
        self.setText(text)
        self.setFixedWidth(20)
        summa: int = summarize(1, 3)
        self.setToolTip(f"Click to get lucky sum: {summa}")
        self.setStyleSheet("border: none;")
        self.clicked.connect(self.__on_click)
        self.__clicked: bool = False

    def is_clicked(self):
        return self.__clicked

    def __on_click(self) -> None:
        self.__clicked: bool = True
