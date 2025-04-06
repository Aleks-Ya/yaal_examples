from PyQt6.QtGui import QPalette, QColor
from PyQt6.QtWidgets import QApplication


def test_get_palette(qapp: QApplication):
    current_palette: QPalette = qapp.palette()
    assert current_palette is not None


def test_log_palette(qapp: QApplication):
    palette: QPalette = qapp.palette()
    palette_str: str = __palette_to_str(palette)
    print(palette_str)


def __palette_to_str(palette: QPalette) -> str:
    groups: list[str] = []
    for group in QPalette.ColorGroup:
        colors: list[str] = []
        for role in QPalette.ColorRole:
            color: QColor = palette.color(role)
            colors.append(f"{role.name}={color.name()}")
        groups.append(f"{group.name}: {','.join(colors)}")
    return QPalette.__name__ + "{" + ", ".join(groups) + "}"
