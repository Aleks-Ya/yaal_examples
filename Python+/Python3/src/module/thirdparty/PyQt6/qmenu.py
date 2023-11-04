import sys

from PyQt6 import QtCore
from PyQt6.QtGui import QAction, QKeySequence
from PyQt6.QtWidgets import QApplication, QLabel, QMainWindow, QMenu, QMessageBox


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        label = QLabel("Hello, World!")
        label.setAlignment(QtCore.Qt.AlignmentFlag.AlignCenter)
        self.setCentralWidget(label)

        menubar = self.menuBar()

        file_menu = QMenu('File', self)
        menubar.addMenu(file_menu)

        message_action = QAction('Show message', self)
        message_action.setShortcut(QKeySequence('Ctrl+M'))
        message_action.triggered.connect(self.show_message)
        file_menu.addAction(message_action)

        exit_action = QAction('Exit', self)
        exit_action.setShortcut(QKeySequence('Ctrl+Q'))
        exit_action.triggered.connect(self.close)
        file_menu.addAction(exit_action)

    def show_message(self):
        QMessageBox.information(self, 'Message', 'This is a message box')


def main():
    app = QApplication(sys.argv)
    main_window = MainWindow()
    main_window.show()
    sys.exit(app.exec())


if __name__ == "__main__":
    main()
