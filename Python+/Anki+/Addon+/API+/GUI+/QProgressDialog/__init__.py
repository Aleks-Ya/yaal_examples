from threading import Thread
from time import sleep

from aqt import mw
from aqt.qt import QProgressDialog
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common import menu


def __cancelled():
    showInfo("Operation was cancelled")


def __show_dialog_endless() -> None:
    progress_dialog: QProgressDialog = QProgressDialog("Working endless...", "Cancel", 0, 0, mw)
    # noinspection PyUnresolvedReferences
    progress_dialog.setWindowTitle("Progress without range")
    # noinspection PyUnresolvedReferences
    progress_dialog.canceled.connect(__cancelled)
    # noinspection PyUnresolvedReferences
    progress_dialog.show()


def __increment_progress(progress_dialog: QProgressDialog):
    while not progress_dialog.wasCanceled() and progress_dialog.value() < progress_dialog.maximum():
        mw.taskman.run_on_main(lambda: progress_dialog.setValue(progress_dialog.value() + 1))
        sleep(1)


def __show_dialog_range() -> None:
    progress_dialog: QProgressDialog = QProgressDialog("Working in range...", "Cancel", 0, 5, mw)
    # noinspection PyUnresolvedReferences
    progress_dialog.setWindowTitle("Progress with range")
    # noinspection PyUnresolvedReferences
    progress_dialog.canceled.connect(__cancelled)
    # noinspection PyUnresolvedReferences
    progress_dialog.finished.connect(lambda: showInfo("Operation was finished"))
    # noinspection PyUnresolvedReferences
    progress_dialog.show()
    thread: Thread = Thread(target=__increment_progress, args=(progress_dialog,))
    thread.start()


if enabled(False):
    menu.add_mw_menu_item("Show progress dialog (endless)", __show_dialog_endless)
    menu.add_mw_menu_item("Show progress dialog (range) 5", __show_dialog_range)
