from PyQt6.QtWidgets import QProgressBar

from tests.module.thirdparty.PyQt6 import app

with app():
    progress_bar: QProgressBar = QProgressBar()
    progress_bar.setRange(0, 100)
    progress_bar.setValue(70)
    progress_bar.show()
