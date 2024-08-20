import sys
from contextlib import contextmanager
from typing import Generator

from PyQt6.QtCore import QSize, QRect, QSizeF
from PyQt6.QtGui import QTextDocument
from PyQt6.QtWidgets import QApplication, QWidget, QSizePolicy, QLayout, QAbstractScrollArea


@contextmanager
def app() -> Generator[QApplication, QApplication, QApplication]:
    try:
        application: QApplication = QApplication([])
        yield application
        sys.exit(application.exec())
    finally:
        pass


@contextmanager
def window() -> Generator[QWidget, QWidget, QWidget]:
    try:
        application: QApplication = QApplication([])
        widget: QWidget = QWidget()
        yield widget
        widget.show()
        sys.exit(application.exec())
    finally:
        pass


def assert_widget_size(widget: QWidget, size: QSize, minimum_size: QSize, maximum_size: QSize,
                       size_hint: QSize, minimum_size_hint: QSize, size_policy: QSizePolicy, size_increment: QSize,
                       base_size: QSize, frame_size: QSize, geometry: QRect) -> None:
    assert widget.size() == size, f"Size: {widget.size()} != {size}"
    assert widget.minimumSize() == minimum_size, f"Minimum size: {widget.minimumSize()} != {minimum_size}"
    assert widget.maximumSize() == maximum_size, f"Maximum size: {widget.maximumSize()} != {maximum_size}"
    assert widget.sizeHint() == size_hint, f"Size hint: {widget.sizeHint()} != {size_hint}"
    assert widget.minimumSizeHint() == minimum_size_hint, f"Minimum size hint: {widget.minimumSizeHint()} != {minimum_size_hint}"
    assert widget.sizePolicy() == size_policy, f"Size policy: {widget.sizePolicy()} != {size_policy}"
    assert widget.sizeIncrement() == size_increment, f"Size increment: {widget.sizeIncrement()} != {size_increment}"
    assert widget.baseSize() == base_size, f"Base size: {widget.baseSize()} != {base_size}"
    assert widget.frameSize() == frame_size, f"Frame size: {widget.frameSize()} != {frame_size}"
    assert widget.geometry() == geometry, f"Geometry: {widget.geometry()} != {geometry}"


def assert_layout_size(layout: QLayout, minimum_size: QSize, maximum_size: QSize,
                       total_minimum_size: QSize, total_maximum_size: QSize, size_hint: QSize,
                       size_constraint: QLayout.SizeConstraint, geometry: QRect) -> None:
    assert layout.minimumSize() == minimum_size, f"Minimum size: {layout.minimumSize()} != {minimum_size}"
    assert layout.maximumSize() == maximum_size, f"Maximum size: {layout.maximumSize()} != {maximum_size}"
    assert layout.totalMinimumSize() == minimum_size, f"Total minimum size: {layout.totalMinimumSize()} != {total_minimum_size}"
    assert layout.totalMaximumSize() == maximum_size, f"Total maximum size: {layout.totalMaximumSize()} != {total_maximum_size}"
    assert layout.sizeHint() == size_hint, f"Size hint: {layout.sizeHint()} != {size_hint}"
    assert layout.sizeConstraint() == size_constraint, f"Size hint: {layout.sizeConstraint()} != {size_constraint}"
    assert layout.geometry() == geometry, f"Geometry: {layout.geometry()} != {geometry}"


def assert_text_document_size(text_document: QTextDocument, size: QSizeF, page_size: QSizeF) -> None:
    assert text_document.size() == size, f"{text_document.size()} != {size}"
    assert text_document.pageSize() == page_size, f"{text_document.pageSize()} != {page_size}"


def assert_scroll_area_size(scroll_area: QAbstractScrollArea,
                            size_adjust_policy: QAbstractScrollArea.SizeAdjustPolicy) -> None:
    assert scroll_area.sizeAdjustPolicy() == size_adjust_policy, f"{scroll_area.sizeAdjustPolicy()} != {size_adjust_policy}"
