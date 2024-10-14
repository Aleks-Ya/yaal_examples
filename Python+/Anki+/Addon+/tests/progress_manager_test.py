import sys
from unittest.mock import MagicMock

from aqt import AnkiQt, QApplication, QWidget
from aqt.progress import ProgressManager


def test_update():
    application: QApplication = QApplication([])
    parent: QWidget = QWidget()
    mw: AnkiQt = MagicMock()
    progress_manager: ProgressManager = ProgressManager(mw)
    progress_manager.start(100, 0, "Label 1", parent=parent)
    progress_manager.update("Label 2", 25, True, True, 100)
    progress_manager.finish()
    sys.exit(application.exec())
