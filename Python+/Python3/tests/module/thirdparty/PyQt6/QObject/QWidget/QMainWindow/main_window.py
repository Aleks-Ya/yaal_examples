import sys

from PyQt6 import QtCore
from PyQt6.QtGui import QAction
from PyQt6.QtWidgets import QApplication, QLabel, QMainWindow, QMenu


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initialize_ui()

    def initialize_ui(self):
        self.setWindowTitle("Main Window")
        label: QLabel = QLabel("Hello, World!")
        label.setAlignment(QtCore.Qt.AlignmentFlag.AlignCenter)
        self.setCentralWidget(label)
        self.statusBar().showMessage("Status Bar Ready")
        self.setGeometry(300, 300, 400, 300)
        new_action: QAction = QAction('New', self)
        open_action: QAction = QAction('Open', self)

        file_menu: QMenu = self.menuBar().addMenu('File')
        file_menu.addAction(new_action)
        file_menu.addAction(open_action)

        assert label.parent() == self
        assert label.parentWidget() == self
        assert label.nativeParentWidget() is None
        assert file_menu.parent() == self.menuBar()
        assert file_menu.parentWidget() == self.menuBar()
        assert file_menu.nativeParentWidget() is None
        assert new_action.parent() == self
        assert open_action.parent() == self


if __name__ == "__main__":
    app: QApplication = QApplication(sys.argv)
    main_window: MainWindow = MainWindow()
    assert main_window.parent() is None
    assert main_window.parentWidget() is None
    assert main_window.nativeParentWidget() is None
    main_window.show()
    sys.exit(app.exec())
