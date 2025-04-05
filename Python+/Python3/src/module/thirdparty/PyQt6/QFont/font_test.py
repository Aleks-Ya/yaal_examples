from PyQt6.QtGui import QFontDatabase
from pytestqt.qtbot import QtBot


def test_list_all_fonts(qtbot: QtBot):
    assert qtbot
    families: list[str] = QFontDatabase.families()

    for family in families:
        print(family)

    print(f"\nTotal families number: {len(families)}\n")

    styles: list[str] = QFontDatabase.styles("FreeMono")
    for style in styles:
        print(f"Style: {style}")


def test_is_font_exist(qtbot: QtBot):
    assert qtbot
    family1: str = "FreeMono"
    exists1: bool = family1 in QFontDatabase.families()
    assert exists1

    family2: str = "Consolas"
    exists2: bool = family2 in QFontDatabase.families()
    assert not exists2
