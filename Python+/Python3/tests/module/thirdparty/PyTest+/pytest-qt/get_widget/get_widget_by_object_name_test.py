from PyQt6.QtWidgets import QVBoxLayout, QLabel, QWidget, QDialog, QPushButton
from pytestqt.qtbot import QtBot


def test_get_nested_element(qtbot: QtBot):
    window: QWidget = __create_window()
    qtbot.addWidget(window)

    label_act: QLabel = window.findChild(QLabel, ObjectNames.Layout.label)
    assert label_act.text() == "Hello, World!"

    ok_button: QPushButton = window.findChild(QPushButton, ObjectNames.Layout.Dialog.ok_button)
    assert ok_button.text() == "Ok"

    close_button: QPushButton = window.findChild(QPushButton, ObjectNames.Layout.Dialog.close_button)
    assert close_button.text() == "Close"

    dialog_label: QLabel = window.findChild(QLabel, ObjectNames.Layout.Dialog.label)
    assert dialog_label.text() == "This is a dialog"


def __create_window():
    label: QLabel = __create_label()
    dialog: QDialog = __create_dialog()
    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(label)
    layout.addWidget(dialog)
    window: QWidget = QWidget()
    window.setLayout(layout)
    return window


class ObjectNames:
    class Layout:
        label: str = "layout.label"

        class Dialog:
            ok_button: str = "dialog.ok_button"
            close_button: str = "dialog.close_button"
            label: str = "dialog.label"


def __create_label() -> QLabel:
    label: QLabel = QLabel("Hello, World!")
    label.setObjectName(ObjectNames.Layout.label)
    return label


def __create_dialog() -> QDialog:
    label: QLabel = QLabel("This is a dialog")
    label.setObjectName(ObjectNames.Layout.Dialog.label)

    ok_button: QPushButton = QPushButton("Ok")
    ok_button.setObjectName(ObjectNames.Layout.Dialog.ok_button)

    close_button: QPushButton = QPushButton("Close")
    close_button.setObjectName(ObjectNames.Layout.Dialog.close_button)

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(label)
    layout.addWidget(ok_button)
    layout.addWidget(close_button)

    dialog: QDialog = QDialog()
    dialog.setLayout(layout)

    return dialog
