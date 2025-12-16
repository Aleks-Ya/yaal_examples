from PyQt6.QtCore import QObject, pyqtSignal
from PyQt6.QtWidgets import QPushButton

from tests.module.thirdparty.PyQt6 import vbox


class StatusLog(QObject):
    status_signal: pyqtSignal = pyqtSignal()

    def __init__(self, status: str):
        super().__init__()
        self.status: str = status
        self.status_signal.connect(self.__log_start)

    def __log_start(self) -> None:
        print(f"Status: {self.status}")


with vbox() as vbox:
    start_status: StatusLog = StatusLog("started")
    stop_status: StatusLog = StatusLog("stopped")

    start_button: QPushButton = QPushButton("Start")
    start_button.clicked.connect(start_status.status_signal.emit)

    stop_button: QPushButton = QPushButton("Stop")
    stop_button.clicked.connect(stop_status.status_signal.emit)

    vbox.addWidget(start_button)
    vbox.addWidget(stop_button)
