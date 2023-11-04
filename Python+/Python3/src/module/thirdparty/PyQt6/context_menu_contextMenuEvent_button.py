import sys

from PyQt6.QtGui import QAction
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
        self.button = QPushButton("Right Click Me")

        layout = QVBoxLayout()
        layout.addWidget(self.button)

        central_widget = QWidget()
        central_widget.setLayout(layout)
        self.setCentralWidget(central_widget)

    def contextMenuEvent(self, event):
        context_menu = QMenu(self)

        option1 = QAction('Option 1', self)
        option1.triggered.connect(self.option1_action)
        context_menu.addAction(option1)

        option2 = QAction('Option 2', self)
        option2.triggered.connect(self.option2_action)
        context_menu.addAction(option2)

        context_menu.exec(event.globalPos())

    def option1_action(self):
        QMessageBox.information(self, 'Message', 'You selected Option 1')

    def option2_action(self):
        QMessageBox.information(self, 'Message', 'You selected Option 2')


def main():
    app = QApplication(sys.argv)
    main_window = MainWindow()
    main_window.show()
    sys.exit(app.exec())


if __name__ == "__main__":
    main()
