from PyQt6.QtWidgets import QDialog

from src.module.thirdparty.PyQt6 import app

with app():
    dialog: QDialog = QDialog()
    dialog.setWindowTitle("Close application dialog")
    dialog.finished.connect(app.quit)
    dialog.show()
