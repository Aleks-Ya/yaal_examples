from PyQt6.QtWidgets import QStyle

from src.module.thirdparty.PyQt6 import app

with app() as app:
    app_style: QStyle = app.style()
    print(app_style.objectName())
