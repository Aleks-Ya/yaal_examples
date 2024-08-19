import sys
from contextlib import contextmanager
from typing import Generator

from PyQt6.QtWidgets import QApplication, QWidget


@contextmanager
def app() -> Generator[None, QApplication, None]:
    try:
        application: QApplication = QApplication([])
        yield application
        sys.exit(application.exec())
    finally:
        pass


@contextmanager
def window() -> Generator[None, QWidget, None]:
    try:
        application: QApplication = QApplication([])
        widget: QWidget = QWidget()
        yield widget
        widget.show()
        sys.exit(application.exec())
    finally:
        pass
