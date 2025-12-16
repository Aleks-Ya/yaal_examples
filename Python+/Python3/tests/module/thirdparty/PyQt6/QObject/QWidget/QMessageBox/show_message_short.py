from PyQt6.QtWidgets import QMessageBox

from tests.module.thirdparty.PyQt6 import app

with app():
    QMessageBox.information(None, "My message title", "My message text")
