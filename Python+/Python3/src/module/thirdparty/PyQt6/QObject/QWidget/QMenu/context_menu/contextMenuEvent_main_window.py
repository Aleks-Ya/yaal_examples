import sys

from PyQt6.QtGui import QAction
from PyQt6.QtWidgets import QApplication
from PyQt6.QtWidgets import QMainWindow
from PyQt6.QtWidgets import QMenu
from PyQt6.QtWidgets import QMessageBox


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()

    def contextMenuEvent(self, event):
        option1: QAction = QAction('Option 1', self)
        option1.triggered.connect(self.option1_action)

        option2: QAction = QAction('Option 2', self)
        option2.triggered.connect(self.option2_action)

        context_menu: QMenu = QMenu(self)
        context_menu.addAction(option1)
        context_menu.addAction(option2)
        context_menu.exec(event.globalPos())

    def option1_action(self):
        QMessageBox.information(self, 'Message', 'You selected Option 1')

    def option2_action(self):
        QMessageBox.information(self, 'Message', 'You selected Option 2')


def main():
    app: QApplication = QApplication(sys.argv)
    main_window: MainWindow = MainWindow()
    main_window.show()
    sys.exit(app.exec())


if __name__ == "__main__":
    main()
