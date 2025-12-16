from PyQt6.QtCore import Qt
from PyQt6.QtGui import QPalette
from PyQt6.QtWidgets import QPushButton

from tests.module.thirdparty.PyQt6 import app

with app() as app:
    button: QPushButton = QPushButton('Click Me')
    dark_palette = QPalette()
    dark_palette.setColorGroup(
        QPalette.ColorGroup.Active,
        Qt.GlobalColor.darkGray,  # foreground
        Qt.GlobalColor.darkGray,  # button
        Qt.GlobalColor.lightGray,  # light
        Qt.GlobalColor.black,  # dark
        Qt.GlobalColor.gray,  # mid
        Qt.GlobalColor.darkGray,  # text
        Qt.GlobalColor.black,  # bright_text
        Qt.GlobalColor.white,  # base
        Qt.GlobalColor.white  # background
    )
    button.setPalette(dark_palette)
    button.show()
