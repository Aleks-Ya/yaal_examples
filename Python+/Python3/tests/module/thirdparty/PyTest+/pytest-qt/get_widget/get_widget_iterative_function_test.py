from typing import Optional

from PyQt6.QtWidgets import QVBoxLayout, QLabel, QWidget, QDialog, QPushButton
from pytestqt.qtbot import QtBot


def test_get_nested_element(qtbot: QtBot):
    window: QWidget = __create_window()
    qtbot.addWidget(window)

    label: QLabel = get_nested_child(window, [QLabel])
    assert label.text() == "Hello, World!"

    dialog_label: QLabel = get_nested_child(window, [QDialog, QLabel])
    assert dialog_label.text() == "This is a dialog"

    dialog_button: QPushButton = get_nested_child(window, [QDialog, QPushButton])
    assert dialog_button.text() == "Close"


def get_nested_child(widget: QWidget, widget_classes: list[type[QWidget]]) -> Optional:
    current_widget: QWidget = widget
    for widget_class in widget_classes:
        current_widget = current_widget.findChild(widget_class)
        if current_widget is None:
            return None
    return current_widget


def __create_window():
    label: QLabel = QLabel("Hello, World!")
    dialog: QDialog = __create_dialog()
    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(label)
    layout.addWidget(dialog)
    window: QWidget = QWidget()
    window.setLayout(layout)
    return window


def __create_dialog() -> QDialog:
    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(QLabel("This is a dialog"))
    layout.addWidget(QPushButton("Close"))

    dialog: QDialog = QDialog()
    dialog.setLayout(layout)
    return dialog
