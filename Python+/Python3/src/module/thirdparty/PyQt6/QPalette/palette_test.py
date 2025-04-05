from PyQt6.QtGui import QPalette

from src.module.thirdparty.PyQt6 import app

with app() as app:
    current_palette: QPalette = app.palette()
    assert current_palette is not None
