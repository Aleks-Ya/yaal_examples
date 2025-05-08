import re
from unittest.mock import MagicMock

from PyQt6.QtCore import QTimer
from PyQt6.QtWidgets import QWidget
from pytest import raises


def test_mock_parent():
    # application: QApplication = QApplication([])
    parent: QWidget = MagicMock()
    with raises(TypeError, match=re.escape(
            "QTimer(parent: Optional[QObject] = None): argument 'parent' has unexpected type 'MagicMock'")):
        QTimer(parent=parent)
