import threading
from threading import Thread

from PyQt6.QtCore import QThread, pyqtSignal
from PyQt6.QtWidgets import QProgressDialog, QApplication, QPushButton

from src.module.thirdparty.PyQt6 import window


class CountDownWorker(QThread):
    started_signal: pyqtSignal = pyqtSignal()
    next_count_signal: pyqtSignal = pyqtSignal(int)
    finished_signal: pyqtSignal = pyqtSignal()

    def __init__(self, min_value: int, max_value: int) -> None:
        super().__init__()
        self.min_value: int = min_value
        self.max_value: int = max_value
        print(f"CountDownWorker was instantiated ({threading.current_thread().name})")

    def run(self) -> None:
        print(f"Emit started_signal ({threading.current_thread().name})")
        # noinspection PyUnresolvedReferences
        self.started_signal.emit()
        for i in range(self.min_value, self.max_value + 1, 1):
            self.sleep(1)
            print(f"Emit next_count_signal {i} ({threading.current_thread().name})")
            # noinspection PyUnresolvedReferences
            self.next_count_signal.emit(i)
        # noinspection PyUnresolvedReferences
        print(f"Emit finished_signal ({threading.current_thread().name})")
        # noinspection PyUnresolvedReferences
        self.finished_signal.emit()


def update_progress_dialog(value: int) -> None:
    print(f"Update progress dialog: {value} ({threading.current_thread().name})")
    progress_dialog.setValue(value)


with window() as window:
    print(f"Main started ({threading.current_thread().name})")
    __min_value: int = 0
    __max_value: int = 5

    progress_dialog: QProgressDialog = QProgressDialog("Working...", "Cancel", __min_value, __max_value, window)
    progress_dialog.setValue(__max_value)
    # noinspection PyUnresolvedReferences
    progress_dialog.canceled.connect(QApplication.instance().quit)

    worker: CountDownWorker = CountDownWorker(__min_value, __max_value)
    # noinspection PyUnresolvedReferences
    worker.started_signal.connect(progress_dialog.show)
    # noinspection PyUnresolvedReferences
    worker.next_count_signal.connect(update_progress_dialog)
    worker.finished.connect(lambda: print(f"Done ({threading.current_thread().name})"))

    button: QPushButton = QPushButton('Start background work', window)
    # noinspection PyUnresolvedReferences
    button.clicked.connect(worker.start)

    window.setMinimumSize(600, 400)
