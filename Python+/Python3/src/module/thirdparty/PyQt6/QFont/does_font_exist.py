from PyQt6.QtGui import QFontDatabase

from src.module.thirdparty.PyQt6 import app

with app():
    family1: str = "FreeMono"
    exists1: bool = family1 in QFontDatabase.families()
    assert exists1

    family2: str = "Consolas"
    exists2: bool = family2 in QFontDatabase.families()
    assert not exists2
