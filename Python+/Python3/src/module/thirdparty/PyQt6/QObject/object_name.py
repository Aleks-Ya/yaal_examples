import unittest

from PyQt6.QtWidgets import QApplication, QLabel


class TestObjectName(unittest.TestCase):
    def setUp(self):
        self.app = QApplication([])

    def tearDown(self):
        self.app.quit()

    def test_default_name(self):
        label: QLabel = QLabel('Hello')
        self.assertTrue("", label.objectName())

    def test_custom_name(self):
        label: QLabel = QLabel('Hello')
        label.setObjectName("greeting")
        self.assertTrue("greeting", label.objectName())


if __name__ == "__main__":
    unittest.main()
