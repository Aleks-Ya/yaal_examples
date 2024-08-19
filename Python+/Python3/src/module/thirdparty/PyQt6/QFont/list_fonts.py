from PyQt6.QtGui import QFontDatabase

from src.module.thirdparty.PyQt6 import app

with app():
    families: list[str] = QFontDatabase.families()

    for family in families:
        print(family)

    print(f"\nTotal families number: {len(families)}\n")

    styles: list[str] = QFontDatabase.styles("FreeMono")
    for style in styles:
        print(f"Style: {style}")
