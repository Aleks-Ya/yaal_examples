import threading

from PyQt6.QtCore import QThread
from PyQt6.QtWidgets import QApplication
from pytestqt.qtbot import QtBot


# noinspection PyUnusedLocal
def test_thread_name(qtbot: QtBot):
    t: QThread = QApplication.instance().thread()
    assert t.objectName() == ""
    assert threading.current_thread().name == "MainThread"
