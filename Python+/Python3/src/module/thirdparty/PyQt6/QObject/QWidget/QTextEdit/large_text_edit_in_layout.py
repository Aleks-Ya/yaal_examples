from PyQt6.QtCore import QSize, QSizeF, QRect
from PyQt6.QtWidgets import QTextEdit, QSizePolicy, QWIDGETSIZE_MAX, QAbstractScrollArea, QVBoxLayout, QLayout

from src.module.thirdparty.PyQt6 import assert_widget_size, window, assert_layout_size, assert_text_document_size, \
    assert_scroll_area_size

with window() as window:
    text_edit: QTextEdit = QTextEdit()
    text_edit.setText("abc " * 200)
    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(text_edit)
    window.setLayout(layout)

    assert_widget_size(
        widget=text_edit,
        size=QSize(640, 480),
        minimum_size=QSize(0, 0),
        maximum_size=QSize(QWIDGETSIZE_MAX, QWIDGETSIZE_MAX),
        size_hint=QSize(256, 192),
        minimum_size_hint=QSize(70, 70),
        size_policy=QSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding),
        size_increment=QSize(0, 0),
        base_size=QSize(0, 0),
        frame_size=QSize(640, 480),
        geometry=QRect(0, 0, 640, 480))

    assert_scroll_area_size(text_edit, QAbstractScrollArea.SizeAdjustPolicy.AdjustIgnored)

    assert_text_document_size(text_edit.document(),
                              size=QSizeF(0, 0),
                              page_size=QSizeF(0, 0))

    assert_widget_size(
        widget=text_edit.viewport(),
        size=QSize(638, 478),
        minimum_size=QSize(0, 0),
        maximum_size=QSize(QWIDGETSIZE_MAX, QWIDGETSIZE_MAX),
        size_hint=QSize(-1, -1),
        minimum_size_hint=QSize(-1, -1),
        size_policy=QSizePolicy(QSizePolicy.Policy.Preferred, QSizePolicy.Policy.Preferred),
        size_increment=QSize(0, 0),
        base_size=QSize(0, 0),
        frame_size=QSize(638, 478),
        geometry=QRect(1, 1, 638, 478))

    assert_layout_size(
        layout=layout,
        minimum_size=QSize(92, 92),
        maximum_size=QSize(524287, 524287),
        total_minimum_size=QSize(0, 0),
        total_maximum_size=QSize(0, 0),
        size_hint=QSize(278, 214),
        size_constraint=QLayout.SizeConstraint.SetDefaultConstraint,
        geometry=QRect())

    assert_widget_size(
        widget=window,
        size=QSize(640, 480),
        minimum_size=QSize(0, 0),
        maximum_size=QSize(QWIDGETSIZE_MAX, QWIDGETSIZE_MAX),
        size_hint=QSize(278, 214),
        minimum_size_hint=QSize(92, 92),
        size_policy=QSizePolicy(QSizePolicy.Policy.Preferred, QSizePolicy.Policy.Preferred),
        size_increment=QSize(0, 0),
        base_size=QSize(0, 0),
        frame_size=QSize(640, 480),
        geometry=QRect(0, 0, 640, 480))
