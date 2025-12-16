from abc import abstractmethod

from PyQt6.QtWidgets import QDialog, QVBoxLayout, QLabel, QApplication

from tests.module.thirdparty.PyQt6 import app


class Event:
    def __init__(self, text: str):
        self.text: str = text


class Listener:
    @abstractmethod
    def on_changed(self, event: Event):
        ...


class MyDialog(QDialog, Listener):
    def __init__(self):
        super().__init__()
        self.__label: QLabel = QLabel("Old text")

        layout: QVBoxLayout = QVBoxLayout()
        layout.addWidget(self.__label)

        self.setLayout(layout)
        # noinspection PyUnresolvedReferences
        self.finished.connect(QApplication.quit)

    def on_changed(self, event: Event):
        self.__label.setText(event.text)

    def show_dialog(self):
        self.show()


with app():
    __dialog: MyDialog = MyDialog()
    __dialog.show_dialog()

    __listener: Listener = __dialog
    __event: Event = Event("New text")
    __listener.on_changed(__event)
