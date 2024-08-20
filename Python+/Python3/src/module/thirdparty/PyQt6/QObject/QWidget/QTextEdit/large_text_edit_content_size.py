from PyQt6.QtCore import QSize, QRect, QSizeF, QMargins
from PyQt6.QtGui import QTextDocument, QFontMetrics
from PyQt6.QtWidgets import QTextEdit, QSizePolicy, QWIDGETSIZE_MAX, QAbstractScrollArea

from src.module.thirdparty.PyQt6 import app, assert_widget_size, assert_scroll_area_size, assert_text_document_size

with app():
    text_edit: QTextEdit = QTextEdit()
    text_edit.setText("abc" * 100 + " " + "xyz" * 100)
    text_edit.show()
    document: QTextDocument = text_edit.document()
    document.adjustSize()
    document_size: QSize = document.size().toSize()

    content_margins: QMargins = text_edit.contentsMargins()
    font_metrics: QFontMetrics = text_edit.fontMetrics()
    line_spacing: int = font_metrics.lineSpacing()
    extra_height: int = content_margins.top() + content_margins.bottom() + line_spacing

    new_size: QSize = QSize(document_size.width(), document_size.height() + extra_height)
    text_edit.resize(new_size)

    assert_widget_size(
        widget=text_edit,
        size=QSize(379, 248),
        minimum_size=QSize(0, 0),
        maximum_size=QSize(QWIDGETSIZE_MAX, QWIDGETSIZE_MAX),
        size_hint=QSize(256, 192),
        minimum_size_hint=QSize(70, 70),
        size_policy=QSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding),
        size_increment=QSize(0, 0),
        base_size=QSize(0, 0),
        frame_size=QSize(385, 281),
        geometry=QRect(0, 0, 379, 248))

    assert_scroll_area_size(scroll_area=text_edit,
                            size_adjust_policy=QAbstractScrollArea.SizeAdjustPolicy.AdjustIgnored)

    assert_text_document_size(text_document=text_edit.document(),
                              size=QSizeF(377.0, 246.0),
                              page_size=QSizeF(377.0, -1.0))

    assert_widget_size(
        widget=text_edit.viewport(),
        size=QSize(377, 246),
        minimum_size=QSize(0, 0),
        maximum_size=QSize(QWIDGETSIZE_MAX, QWIDGETSIZE_MAX),
        size_hint=QSize(-1, -1),
        minimum_size_hint=QSize(-1, -1),
        size_policy=QSizePolicy(QSizePolicy.Policy.Preferred, QSizePolicy.Policy.Preferred),
        size_increment=QSize(0, 0),
        base_size=QSize(0, 0),
        frame_size=QSize(377, 246),
        geometry=QRect(1, 1, 377, 246))
