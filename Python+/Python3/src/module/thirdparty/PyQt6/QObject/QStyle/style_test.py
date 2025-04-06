from PyQt6.QtWidgets import QApplication, QStyle, QStyleFactory
from pytestqt.qtbot import QtBot


def test_get_default_style(qtbot: QtBot):
    assert qtbot
    style: QStyle = QApplication.style()
    print(style.objectName())
    assert style.objectName() == 'fusion'


def test_get_style_by_name(qtbot: QtBot):
    assert qtbot
    style: QStyle = QStyleFactory.create('Fusion')
    print(style.objectName())


def test_list_all_styles(qtbot: QtBot):
    assert qtbot
    styles: list[str] = QStyleFactory.keys()
    print(styles)
