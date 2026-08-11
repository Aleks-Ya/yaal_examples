from PyQt6.QtWidgets import QVBoxLayout, QLabel, QWidget, QDialog, QPushButton
from pytestqt.qtbot import QtBot
from PyQtPath.pyqt6_path import get_nested_child


def test_get_nested_element(qtbot: QtBot):
    window: QWidget = __create_window()
    qtbot.addWidget(window)

    label: QLabel = get_nested_child(window, QLabel)
    assert label.text() == "Hello, World!"

    dialog_label: QLabel = get_nested_child(window, QDialog, QLabel)
    assert dialog_label.text() == "This is a dialog"

    ok_button: QPushButton = get_nested_child(window, QDialog, QPushButton, 0)
    assert ok_button.text() == "Ok"

    close_button: QPushButton = get_nested_child(window, QDialog, QPushButton, 1)
    assert close_button.text() == "Close"


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
    layout.addWidget(QPushButton("Ok"))
    layout.addWidget(QPushButton("Close"))

    dialog: QDialog = QDialog()
    dialog.setLayout(layout)
    return dialog
