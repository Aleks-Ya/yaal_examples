from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QCheckBox, QVBoxLayout, QWidget
from pytestqt.qtbot import QtBot


class State:
    def __init__(self):
        self.checked = False

    def on_state_changed(self, _: int):
        self.checked = True


def test_check_checkbox_alone(qtbot: QtBot):
    state: State = State()
    checkbox: QCheckBox = QCheckBox()
    # noinspection PyUnresolvedReferences
    checkbox.stateChanged.connect(state.on_state_changed)
    checkbox.show()
    qtbot.addWidget(checkbox)
    assert not checkbox.isChecked()
    assert not state.checked
    qtbot.mouseClick(checkbox, Qt.MouseButton.LeftButton)
    assert checkbox.isChecked()
    assert state.checked


def test_check_checkbox_in_widget(qtbot: QtBot):
    state: State = State()
    checkbox: QCheckBox = QCheckBox()
    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(checkbox)
    widget: QWidget = QWidget()
    widget.setLayout(layout)
    widget.show()

    # noinspection PyUnresolvedReferences
    checkbox.stateChanged.connect(state.on_state_changed)
    assert not checkbox.isChecked()
    assert not state.checked
    qtbot.mouseClick(checkbox, Qt.MouseButton.LeftButton)
    assert checkbox.isChecked()
    assert state.checked
