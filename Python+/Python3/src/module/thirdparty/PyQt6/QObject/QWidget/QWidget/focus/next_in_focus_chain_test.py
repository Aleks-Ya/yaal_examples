from PyQt6.QtWidgets import QLineEdit, QVBoxLayout, QWidget
from pytestqt.qtbot import QtBot


def test_next_in_focus_chain(qtbot: QtBot):
    layout: QVBoxLayout = QVBoxLayout()
    line_edit_1: QLineEdit = QLineEdit('Line Edit 1')
    line_edit_2: QLineEdit = QLineEdit('Line Edit 2')
    line_edit_3: QLineEdit = QLineEdit('Line Edit 3')
    line_edit_4: QLineEdit = QLineEdit('Line Edit 4')

    layout.addWidget(line_edit_1)
    layout.addWidget(line_edit_2)
    layout.addWidget(line_edit_3)
    layout.addWidget(line_edit_4)

    window: QWidget = QWidget()
    window.setLayout(layout)

    qtbot.addWidget(window)

    assert line_edit_1.nextInFocusChain() == line_edit_2
    assert line_edit_2.nextInFocusChain() == line_edit_3
    assert line_edit_3.nextInFocusChain() == line_edit_4
    assert line_edit_4.nextInFocusChain() == window
    assert window.nextInFocusChain() == line_edit_1

    assert line_edit_1.previousInFocusChain() == window
    assert line_edit_2.previousInFocusChain() == line_edit_1
    assert line_edit_3.previousInFocusChain() == line_edit_2
    assert line_edit_4.previousInFocusChain() == line_edit_3
    assert window.previousInFocusChain() == line_edit_4
