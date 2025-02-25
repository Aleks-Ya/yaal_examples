import sys

from PyQt6.QtCore import Qt
from PyQt6.QtGui import QAction, QKeySequence
from PyQt6.QtWidgets import QApplication
from PyQt6.QtWidgets import QMainWindow
from PyQt6.QtWidgets import QMenu
from PyQt6.QtWidgets import QMessageBox
from PyQt6.QtWidgets import QPushButton
from PyQt6.QtWidgets import QVBoxLayout
from PyQt6.QtWidgets import QWidget


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()

        self.button: QPushButton = QPushButton("Right Click Me")

        layout: QVBoxLayout = QVBoxLayout()
        layout.addWidget(self.button)

        central_widget: QWidget = QWidget()
        central_widget.setLayout(layout)
        self.setCentralWidget(central_widget)

        self.action1: QAction = QAction("Option 1")
        self.action1.triggered.connect(self.option1_action)
        self.action1.setShortcut(QKeySequence("Ctrl+1"))
        self.action2: QAction = QAction("Option 2")
        self.action2.triggered.connect(self.option2_action)

        self.context_menu: QMenu = QMenu(self)
        self.context_menu.addAction(self.action1)
        self.context_menu.addAction(self.action2)

        self.button.setContextMenuPolicy(Qt.ContextMenuPolicy.CustomContextMenu)
        self.button.customContextMenuRequested.connect(self.show_context_menu)

        self.addAction(self.action1)

    def show_context_menu(self, position):
        self.context_menu.exec(self.button.mapToGlobal(position))

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
