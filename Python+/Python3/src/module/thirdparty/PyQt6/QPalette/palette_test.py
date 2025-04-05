from PyQt6.QtGui import QPalette
from PyQt6.QtWidgets import QApplication


def test_get_palette(qapp: QApplication):
    current_palette: QPalette = qapp.palette()
    assert current_palette is not None
