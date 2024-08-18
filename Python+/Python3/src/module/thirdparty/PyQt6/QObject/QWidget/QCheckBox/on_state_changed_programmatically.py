import unittest

from PyQt6.QtWidgets import QApplication, QCheckBox
from PyQt6.QtCore import Qt


class TestCheckBoxStateChangedProgrammatically(unittest.TestCase):
    current_state: int = -1

    def setUp(self):
        self.app: QApplication = QApplication([])
        self.checkbox: QCheckBox = QCheckBox("Check me!")
        self.checkbox.stateChanged.connect(self.__on_state_changed)

    def tearDown(self):
        self.app.quit()

    def test_set_checked(self):
        # Test initial state
        self.assertEqual(self.current_state, -1)
        self.assertFalse(self.checkbox.isChecked())

        # Test checking the box
        self.checkbox.setChecked(True)
        self.assertTrue(self.checkbox.isChecked())
        self.assertEqual(self.current_state, Qt.CheckState.Checked.value)

        # Test unchecking the box
        self.checkbox.setChecked(False)
        self.assertFalse(self.checkbox.isChecked())
        self.assertEqual(self.current_state, Qt.CheckState.Unchecked.value)

    def test_emit(self):
        # Test initial state
        self.assertEqual(self.current_state, -1)
        self.assertFalse(self.checkbox.isChecked())

        # Test checking the box
        self.checkbox.stateChanged.emit(Qt.CheckState.Checked.value)
        self.assertFalse(self.checkbox.isChecked())
        self.assertEqual(self.current_state, Qt.CheckState.Checked.value)

        # Test unchecking the box
        self.checkbox.stateChanged.emit(Qt.CheckState.Unchecked.value)
        self.assertFalse(self.checkbox.isChecked())
        self.assertEqual(self.current_state, Qt.CheckState.Unchecked.value)

    def __on_state_changed(self, state: int):
        self.current_state = state


if __name__ == "__main__":
    unittest.main()
