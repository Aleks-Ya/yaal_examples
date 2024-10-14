import aqt
from aqt import QWidget, QApplication
from aqt.utils import show_info, MessageBox
from mock.mock import MagicMock
from pytestqt.qtbot import QtBot


def test_show_info_parent(qtbot: QtBot):
    widget: QWidget = QWidget()
    qtbot.addWidget(widget)
    message_box: MessageBox = show_info("hello", parent=widget)
    assert message_box.text() == "hello"


def test_show_info_active_window(qtbot: QtBot):
    widget: QWidget = QWidget()
    qtbot.addWidget(widget)
    aqt.mw = MagicMock()
    aqt.mw.app = QApplication.instance()
    # noinspection PyUnresolvedReferences
    aqt.mw.app.setActiveWindow(widget)
    message_box: MessageBox = show_info("hello")
    assert message_box.text() == "hello"
