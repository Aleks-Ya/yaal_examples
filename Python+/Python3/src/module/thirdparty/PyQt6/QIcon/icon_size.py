from PyQt6.QtCore import QSize
from PyQt6.QtGui import QIcon
from pytestqt.qtbot import QtBot


# noinspection PyUnusedLocal
def test_available_size_512(qtbot: QtBot):
    icon: QIcon = QIcon('info.png')
    sizes: list[QSize] = icon.availableSizes()
    assert sizes == [QSize(512, 512)]


# noinspection PyUnusedLocal
def test_available_size_32(qtbot: QtBot):
    icon: QIcon = QIcon('select.png')
    sizes: list[QSize] = icon.availableSizes()
    assert sizes == [QSize(32, 32)]


# noinspection PyUnusedLocal
def test_available_size_missing_file(qtbot: QtBot):
    icon: QIcon = QIcon('not-exists.png')
    assert icon.availableSizes() == []
