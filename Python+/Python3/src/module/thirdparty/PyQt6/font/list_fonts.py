from PyQt6.QtGui import QFontDatabase
from PyQt6.QtWidgets import QApplication

app: QApplication = QApplication([])
families: list[str] = QFontDatabase.families()

for family in families:
    print(family)

print(f"\nTotal families number: {len(families)}\n")

styles: list[str] = QFontDatabase.styles("FreeMono")
for style in styles:
    print(f"Style: {style}")
