import pytest
from PyQt6.QtCore import Qt
from pytestqt.qtbot import QtBot

from application.ui.info_button import InfoButton


@pytest.fixture
def info_button() -> InfoButton:
    return InfoButton("Click me")


def test_info_button(qtbot: QtBot, info_button: InfoButton):
    assert not info_button.is_clicked()
    qtbot.mouseClick(info_button, Qt.MouseButton.LeftButton)
    assert info_button.is_clicked()
