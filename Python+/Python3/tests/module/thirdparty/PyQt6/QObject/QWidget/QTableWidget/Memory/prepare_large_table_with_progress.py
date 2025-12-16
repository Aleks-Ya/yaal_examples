import threading
from time import sleep

from PyQt6.QtCore import QThread, pyqtSignal
from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QHBoxLayout, QProgressDialog, QApplication

from tests.module.thirdparty.PyQt6 import window


class PrepareTableWorker(QThread):
    started_signal: pyqtSignal = pyqtSignal()
    next_row_signal: pyqtSignal = pyqtSignal(int)
    finished_signal: pyqtSignal = pyqtSignal()

    def __init__(self, __rows: int, __columns: int) -> None:
        super().__init__()
        self.__table: QTableWidget = QTableWidget(__rows, __columns)
        print(f"CountDownWorker was instantiated ({threading.current_thread().name})")

    def run(self) -> None:
        # noinspection PyUnresolvedReferences
        self.started_signal.emit()
        icon: QIcon = QIcon("info.png")
        for row in range(self.__table.rowCount() + 1):
            text_item: QTableWidgetItem = QTableWidgetItem(f"Text {row}")
            self.__table.setItem(row, 0, text_item)

            icon_item: QTableWidgetItem = QTableWidgetItem()
            icon_item.setIcon(icon)
            self.__table.setItem(row, 1, icon_item)
            if row % 100 == 0:
                # noinspection PyUnresolvedReferences
                self.next_row_signal.emit(row)
            sleep(0.01)
        # noinspection PyUnresolvedReferences
        self.finished_signal.emit()

    def get_table(self) -> QTableWidget:
        return self.__table


def update_progress_dialog(value: int) -> None:
    print(f"Update progress dialog: {value} ({threading.current_thread().name})")
    progress_dialog.setValue(value)


def work_finished() -> None:
    print(f"Finished")
    table: QTableWidget = worker.get_table()
    layout.addWidget(table)
    table.show()


with window() as window:
    rows: int = 1000
    columns: int = 2

    progress_dialog: QProgressDialog = QProgressDialog("Working...", "Cancel", 0, rows, window)
    progress_dialog.setValue(rows)
    # noinspection PyUnresolvedReferences
    progress_dialog.canceled.connect(QApplication.instance().quit)

    worker: PrepareTableWorker = PrepareTableWorker(rows, columns)
    # noinspection PyUnresolvedReferences
    worker.started_signal.connect(progress_dialog.show)
    # noinspection PyUnresolvedReferences
    worker.next_row_signal.connect(update_progress_dialog)
    worker.finished.connect(work_finished)
    worker.start()

    layout: QHBoxLayout = QHBoxLayout()
    window.setLayout(layout)
    window.setMinimumSize(500, 300)
