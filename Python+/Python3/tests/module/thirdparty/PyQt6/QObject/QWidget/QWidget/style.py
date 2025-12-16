from PyQt6.QtWidgets import QWidget, QStyle

from tests.module.thirdparty.PyQt6 import app

with app():
    widget: QWidget = QWidget()
    style: QStyle = widget.style()
    print(style.objectName())
    widget.show()
