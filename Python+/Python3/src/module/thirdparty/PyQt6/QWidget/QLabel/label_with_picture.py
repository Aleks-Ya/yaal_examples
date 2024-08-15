import sys

from PyQt6.QtCore import Qt
from PyQt6.QtGui import QPixmap
from PyQt6.QtWidgets import QApplication, QLabel, QWidget


class MainWindow(QWidget):
    def __init__(self):
        super().__init__()
        self.initialize_ui()

    def initialize_ui(self):
        self.setGeometry(200, 100, 400, 300)
        pixmap: QPixmap = QPixmap("info.png").scaled(30, 30, Qt.AspectRatioMode.KeepAspectRatio,
                                                     Qt.TransformationMode.SmoothTransformation)
        label: QLabel = QLabel(self)
        label.setText('TEXT IS NOT DISPLAYED')
        label.setPixmap(pixmap)
        self.show()


if __name__ == '__main__':
    app: QApplication = QApplication(sys.argv)
    window: MainWindow = MainWindow()
    sys.exit(app.exec())
