import sys
import unittest

from PyQt6.QtWidgets import QApplication, QWidget, QPushButton
from PyQt6.QtTest import QTest
from PyQt6.QtCore import Qt


class MyWidget(QWidget):
    def __init__(self):
        super().__init__()
        self.button: QPushButton = QPushButton("Click me", self)
        # noinspection PyUnresolvedReferences
        self.button.clicked.connect(self.on_button_click)
        self.clicked = False

    def on_button_click(self):
        self.clicked = True


class TestMyWidget(unittest.TestCase):
    def setUp(self):
        self.app: QApplication = QApplication(sys.argv)
        self.widget: MyWidget = MyWidget()

    def tearDown(self):
        self.widget = None
        self.app.quit()

    def test_button_click(self):
        QTest.mouseClick(self.widget.button, Qt.MouseButton.LeftButton)
        self.assertTrue(self.widget.clicked)


if __name__ == "__main__":
    unittest.main()
