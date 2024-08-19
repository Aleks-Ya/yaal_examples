from PyQt6.QtWidgets import QWidget

from src.module.thirdparty.PyQt6 import app

with app():
    widget: QWidget = QWidget()
    assert widget.size().width() == 640
    assert widget.size().height() == 480
    widget.resize(300, 200)
    assert widget.size().width() == 300
    assert widget.size().height() == 200
    widget.show()
