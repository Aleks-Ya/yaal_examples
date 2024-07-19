import unittest

from PyQt6.QtWidgets import QApplication, QComboBox


class TestSelectCurrentElementComboBoxProgrammatically(unittest.TestCase):

    def setUp(self):
        self.app: QApplication = QApplication([])

    def tearDown(self):
        self.app.quit()

    def test_set_checked(self):
        combo_box: QComboBox = QComboBox()
        combo_box.addItems(["Option 1", "Option 2", "Option 3"])
        self.assertEqual("Option 1", combo_box.currentText())
        self.assertEqual(0, combo_box.currentIndex())

        combo_box.setCurrentText("Option 2")
        self.assertEqual("Option 2", combo_box.currentText())
        self.assertEqual(1, combo_box.currentIndex())

        combo_box.setCurrentIndex(2)
        self.assertEqual("Option 3", combo_box.currentText())
        self.assertEqual(2, combo_box.currentIndex())


if __name__ == "__main__":
    unittest.main()
