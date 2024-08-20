from PyQt6.QtCore import QSize, QSizeF, QRect
from PyQt6.QtWidgets import QTextEdit, QSizePolicy, QWIDGETSIZE_MAX, QAbstractScrollArea

from src.module.thirdparty.PyQt6 import app, assert_widget_size, assert_text_document_size, assert_scroll_area_size

with app():
    text_edit: QTextEdit = QTextEdit()
    text_edit.setText("abc " * 200)
    text_edit.show()

    assert text_edit.layout() is None

    assert_widget_size(
        widget=text_edit,
        size=QSize(256, 192),
        minimum_size=QSize(0, 0),
        maximum_size=QSize(QWIDGETSIZE_MAX, QWIDGETSIZE_MAX),
        size_hint=QSize(256, 192),
        minimum_size_hint=QSize(70, 70),
        size_policy=QSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding),
        size_increment=QSize(0, 0),
        base_size=QSize(0, 0),
        frame_size=QSize(262, 225),
        geometry=QRect(0, 0, 256, 192))

    assert_scroll_area_size(scroll_area=text_edit,
                            size_adjust_policy=QAbstractScrollArea.SizeAdjustPolicy.AdjustIgnored)

    assert_text_document_size(text_document=text_edit.document(),
                              size=QSizeF(240, 433),
                              page_size=QSizeF(240, -1))

    assert_widget_size(
        widget=text_edit.viewport(),
        size=QSize(240, 190),
        minimum_size=QSize(0, 0),
        maximum_size=QSize(QWIDGETSIZE_MAX, QWIDGETSIZE_MAX),
        size_hint=QSize(-1, -1),
        minimum_size_hint=QSize(-1, -1),
        size_policy=QSizePolicy(QSizePolicy.Policy.Preferred, QSizePolicy.Policy.Preferred),
        size_increment=QSize(0, 0),
        base_size=QSize(0, 0),
        frame_size=QSize(240, 190),
        geometry=QRect(1, 1, 240, 190))
