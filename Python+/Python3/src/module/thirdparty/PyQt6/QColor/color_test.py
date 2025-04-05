from PyQt6.QtGui import QColor


def test_color_from_string():
    color: QColor = QColor.fromString("green")
    assert color is not None


def test_color_from_rgb():
    color: QColor = QColor.fromRgb(0, 255, 0)
    assert color is not None


def test_color_to_name():
    color: QColor = QColor.fromRgb(0, 128, 0)
    color_name: str = color.name(QColor.NameFormat.HexRgb)
    assert color_name == "#008000"
