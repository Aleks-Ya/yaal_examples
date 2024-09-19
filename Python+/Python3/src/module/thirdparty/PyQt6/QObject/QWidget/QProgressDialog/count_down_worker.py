import threading

from PyQt6.QtCore import QThread, pyqtSignal


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
