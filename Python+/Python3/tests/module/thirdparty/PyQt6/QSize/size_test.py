from PyQt6.QtCore import QSize, QMargins


def test_shrunk_by():
    width: int = 100
    height: int = 200
    size: QSize = QSize(width, height)

    left: int = 10
    top: int = 20
    right: int = 30
    bottom: int = 40
    margins: QMargins = QMargins(left, top, right, bottom)

    small_size: QSize = size.shrunkBy(margins)
    assert small_size == QSize(width - left - right, height - top - bottom)
