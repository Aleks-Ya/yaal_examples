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


if __name__ == "__main__":
    app: QApplication = QApplication(sys.argv)
    main_window: MainWindow = MainWindow()
    main_window.show()
    sys.exit(app.exec())
