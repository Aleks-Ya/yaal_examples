import pytest
from PyQt6.QtWidgets import QLabel, QWidget
from pytestqt.qtbot import QtBot

from hello_app import create_widget


@pytest.fixture
def app(qtbot: QtBot):
    widget: QWidget = create_widget()
    qtbot.addWidget(widget)
    return widget


def test_label_text(app: QWidget):
    label: QLabel = app.findChild(QLabel)
    assert label.text() == 'Hello, World!'
