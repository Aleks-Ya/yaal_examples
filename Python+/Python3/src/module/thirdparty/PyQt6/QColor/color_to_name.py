from PyQt6.QtGui import QColor

color: QColor = QColor.fromRgb(0, 128, 0)
color_name: str = color.name(QColor.NameFormat.HexRgb)
assert color_name == "#008000"
