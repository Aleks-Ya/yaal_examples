import pytest
from PyQt6.QtWidgets import QCheckBox, QPushButton, QHBoxLayout
from pytestqt.qtbot import QtBot

from group_vbox import GroupVBox


@pytest.fixture
def checkbox1() -> QCheckBox:
    return QCheckBox("Option 1")


@pytest.fixture
def checkbox2() -> QCheckBox:
    return QCheckBox("Option 2")


@pytest.fixture
def button1() -> QPushButton:
    return QPushButton("Button 1")


@pytest.fixture
def button2() -> QPushButton:
    return QPushButton("Button 2")


@pytest.fixture
def group_box(qtbot: QtBot, button1: QPushButton, button2: QPushButton, checkbox1: QCheckBox,
              checkbox2: QCheckBox) -> GroupVBox:
    button_layout: QHBoxLayout = QHBoxLayout()
    button_layout.addWidget(button1)
    button_layout.addWidget(button2)

    group_box: GroupVBox = GroupVBox('Group VBox options')
    group_box.add_widget(checkbox1)
    group_box.add_widget(checkbox2)
    group_box.add_layout(button_layout)

    qtbot.addWidget(group_box)

    return group_box


def test_set_enabled(group_box: GroupVBox, button1: QPushButton, button2: QPushButton,
                     checkbox1: QCheckBox, checkbox2: QCheckBox):
    assert checkbox1.isEnabled()
    assert checkbox2.isEnabled()
    assert button1.isEnabled()
    assert button2.isEnabled()

    group_box.set_enabled(False)
    assert not checkbox1.isEnabled()
    assert not checkbox2.isEnabled()
    assert not button1.isEnabled()
    assert not button2.isEnabled()

    group_box.set_enabled(True)
    assert checkbox1.isEnabled()
    assert checkbox2.isEnabled()
    assert button1.isEnabled()
    assert button2.isEnabled()
