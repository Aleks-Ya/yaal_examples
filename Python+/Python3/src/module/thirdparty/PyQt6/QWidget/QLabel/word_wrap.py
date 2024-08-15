import sys

from PyQt6.QtWidgets import QApplication, QLabel, QWidget, QVBoxLayout


class MainWindow(QWidget):
    def __init__(self):
        super().__init__()
        self.initialize_ui()

    def initialize_ui(self):
        label_wrap_on: QLabel = QLabel(self)
        label_wrap_on.setText('Long long long long long long long long long long long long long long long text.')
        label_wrap_on.setWordWrap(True)
        label_wrap_off: QLabel = QLabel(self)
        label_wrap_off.setText('Long long long long long long long text.')
        label_wrap_off.setWordWrap(False)
        layout: QVBoxLayout = QVBoxLayout()
        layout.addWidget(label_wrap_on)
        layout.addWidget(label_wrap_off)
        self.setLayout(layout)
        self.show()


if __name__ == '__main__':
    app: QApplication = QApplication(sys.argv)
    window: MainWindow = MainWindow()
    sys.exit(app.exec())
