from PyQt6.QtWidgets import QProgressBar

from tests.module.thirdparty.PyQt6 import app

with app():
    progress_bar: QProgressBar = QProgressBar()
    progress_bar.setRange(0, 0)
    progress_bar.show()
