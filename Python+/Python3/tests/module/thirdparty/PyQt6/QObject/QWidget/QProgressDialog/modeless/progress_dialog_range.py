from threading import Thread
from time import sleep

from PyQt6.QtCore import QMetaObject, Qt
from PyQt6.QtWidgets import QProgressDialog, QApplication

from tests.module.thirdparty.PyQt6 import app

# NOT WORKING

def __increment_progress(dialog: QProgressDialog):
    while dialog and not dialog.wasCanceled() and dialog.value() < dialog.maximum():
        QMetaObject.invokeMethod(progress_dialog, "setValue", Qt.ConnectionType.QueuedConnection, dialog.value() + 1)
        # dialog.setValue(dialog.value() + 1)
        sleep(1)


with app():
    progress_dialog: QProgressDialog = QProgressDialog("Working...", "Cancel", 0, 5)
    progress_dialog.setWindowTitle("Progress")
    # noinspection PyUnresolvedReferences
    progress_dialog.canceled.connect(QApplication.instance().quit)
    # noinspection PyUnresolvedReferences
    progress_dialog.finished.connect(lambda: print("Finished!"))
    progress_dialog.show()
    thread: Thread = Thread(target=__increment_progress, args=(progress_dialog,))
    thread.start()

