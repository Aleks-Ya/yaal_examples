import sys

from PyQt6.QtCore import QCoreApplication
from PyQt6.QtWidgets import QApplication, QWidget


class GetAppWindow(QWidget):
    def __init__(self):
        super().__init__()
        app2: QCoreApplication = QApplication.instance()
        self.setGeometry(200, 100, 400, 300)
        self.setWindowTitle(app2.applicationName())
        self.show()


if __name__ == '__main__':
    app: QApplication = QApplication(sys.argv)
    window: GetAppWindow = GetAppWindow()
    sys.exit(app.exec())
