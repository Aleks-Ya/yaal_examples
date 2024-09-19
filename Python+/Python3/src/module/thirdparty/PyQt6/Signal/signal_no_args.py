from PyQt6.QtCore import QObject, pyqtSignal
from PyQt6.QtWidgets import QPushButton

from src.module.thirdparty.PyQt6 import window


class StatusLog(QObject):
    start_signal = pyqtSignal()

    def __init__(self):
        super().__init__()
        self.start_signal.connect(self.__log_start)

    @staticmethod
    def __log_start() -> None:
        print("Started")


with window() as window:
    status_log: StatusLog = StatusLog()
    button: QPushButton = QPushButton("Start", window)
    button.clicked.connect(status_log.start_signal.emit)
