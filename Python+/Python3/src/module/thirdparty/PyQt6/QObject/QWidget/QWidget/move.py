from PyQt6.QtWidgets import QWidget

from src.module.thirdparty.PyQt6 import app

with app():
    widget: QWidget = QWidget()
    widget.move(300, 200)
    widget.show()

    assert widget.parent() is None
    assert widget.pos().x() == 300
    assert widget.pos().y() == 200
