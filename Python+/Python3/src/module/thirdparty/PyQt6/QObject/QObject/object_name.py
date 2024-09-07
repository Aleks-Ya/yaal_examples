from PyQt6.QtWidgets import QLabel
from pytestqt.qtbot import QtBot


def test_default_name(qtbot: QtBot):
    label: QLabel = QLabel('Hello')
    qtbot.addWidget(label)
    assert label.objectName() == ""


def test_custom_name(qtbot: QtBot):
    label: QLabel = QLabel('Hello')
    label.setObjectName("greeting")
    qtbot.addWidget(label)
    assert label.objectName() == "greeting"
