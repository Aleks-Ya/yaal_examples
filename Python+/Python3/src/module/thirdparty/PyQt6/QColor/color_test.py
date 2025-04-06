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


def test_darker():
    green: QColor = QColor.fromString("green")
    dark_green: QColor = green.darker(300)
    assert green == QColor.fromRgb(0, 128, 0)
    assert dark_green.red() == 0
    assert dark_green.green() == 43
    assert dark_green.blue() == 0


def test_invert():
    green: QColor = QColor.fromString("green")
    inverted: QColor = __invert(green)
    assert inverted.red() == 255
    assert inverted.green() == 127
    assert inverted.blue() == 255


def __invert(color: QColor) -> QColor:
    red: int = 255 - color.red()
    green: int = 255 - color.green()
    blue: int = 255 - color.blue()
    return QColor(red, green, blue)
