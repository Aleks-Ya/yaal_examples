from PyQt6.QtWidgets import QProgressDialog, QApplication, QPushButton

from src.module.thirdparty.PyQt6 import window
from src.module.thirdparty.PyQt6.QObject.QWidget.QProgressDialog.count_down_worker import CountDownWorker


def show_progress_dialog() -> None:
    progress_dialog.show()
    progress_dialog.setValue(__min_value)


with window() as window:
    __min_value: int = 0
    __max_value: int = 5
    progress_dialog: QProgressDialog = QProgressDialog("Working...", "Cancel", __min_value, __max_value, window)
    progress_dialog.setValue(__max_value)
    progress_dialog.setModal(True)
    progress_dialog.setWindowTitle("Progress")
    # noinspection PyUnresolvedReferences
    progress_dialog.canceled.connect(QApplication.instance().quit)

    worker: CountDownWorker = CountDownWorker(__min_value, __max_value)
    # noinspection PyUnresolvedReferences
    worker.started_signal.connect(show_progress_dialog)
    # noinspection PyUnresolvedReferences
    worker.next_count_signal.connect(progress_dialog.setValue)
    worker.finished.connect(lambda: print(f"Done"))

    button: QPushButton = QPushButton('Start background work', window)
    button.clicked.connect(worker.start)
    window.setMinimumSize(600, 400)
