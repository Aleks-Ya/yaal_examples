from PyQt6.QtCore import QObject, pyqtSignal
from PyQt6.QtWidgets import QPushButton

from src.module.thirdparty.PyQt6 import window


class StatusLog(QObject):
    status_signal: pyqtSignal = pyqtSignal(str)

    def __init__(self):
        super().__init__()
        self.status_signal.connect(self.__log_start)

    @staticmethod
    def __log_start(status: str) -> None:
        print(f"Status: {status}")


with window() as window:
    status_log: StatusLog = StatusLog()
    button: QPushButton = QPushButton("Start", window)
    button.clicked.connect(lambda: status_log.status_signal.emit("started"))
