from PyQt6.QtCore import QSize, QRect
from PyQt6.QtWidgets import QWidget, QWIDGETSIZE_MAX, QSizePolicy

from src.module.thirdparty.PyQt6 import app, assert_widget_size

with app():
    widget: QWidget = QWidget()
    assert widget.sizePolicy() == QSizePolicy(QSizePolicy.Policy.Preferred, QSizePolicy.Policy.Preferred)
    widget.show()

    assert_widget_size(
        widget=widget,
        size=QSize(640, 480),
        minimum_size=QSize(0, 0),
        maximum_size=QSize(QWIDGETSIZE_MAX, QWIDGETSIZE_MAX),
        size_hint=QSize(-1, -1),
        minimum_size_hint=QSize(-1, -1),
        size_policy=QSizePolicy(QSizePolicy.Policy.Preferred, QSizePolicy.Policy.Preferred),
        size_increment=QSize(0, 0),
        base_size=QSize(0, 0),
        frame_size=QSize(646, 513),
        geometry=QRect(0, 0, 640, 480))
