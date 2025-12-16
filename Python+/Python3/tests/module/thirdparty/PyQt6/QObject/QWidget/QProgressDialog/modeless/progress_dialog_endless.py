from PyQt6.QtWidgets import QProgressDialog, QApplication

from tests.module.thirdparty.PyQt6 import app

with app():
    progress_dialog: QProgressDialog = QProgressDialog("Working...", "Cancel", 0, 0)
    progress_dialog.setWindowTitle("Progress")
    # noinspection PyUnresolvedReferences
    progress_dialog.canceled.connect(QApplication.instance().quit)
    progress_dialog.show()
