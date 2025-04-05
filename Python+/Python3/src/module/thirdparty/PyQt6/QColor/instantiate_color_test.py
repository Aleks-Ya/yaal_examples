from PyQt6.QtGui import QColor


def test_from_string():
    color: QColor = QColor.fromString("green")
    assert color is not None


def test_from_rgb():
    color: QColor = QColor.fromRgb(0, 255, 0)
    assert color is not None
