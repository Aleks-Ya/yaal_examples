from PyQt6.QtWidgets import QApplication, QComboBox


def test_set_current_item():
    app: QApplication = QApplication([])

    combo_box: QComboBox = QComboBox()
    combo_box.addItems(["Option 1", "Option 2", "Option 3"])
    assert combo_box.currentText() == "Option 1"
    assert combo_box.currentIndex() == 0

    combo_box.setCurrentText("Option 2")
    assert combo_box.currentText() == "Option 2"
    assert combo_box.currentIndex() == 1

    combo_box.setCurrentIndex(2)
    assert combo_box.currentText() == "Option 3"
    assert combo_box.currentIndex() == 2

    app.quit()


def test_set_absent_current_item():
    app: QApplication = QApplication([])

    combo_box: QComboBox = QComboBox()
    combo_box.addItems(["Option 1", "Option 2", "Option 3"])
    assert combo_box.currentText() == "Option 1"
    assert combo_box.currentIndex() == 0

    combo_box.setCurrentText("Option 4")
    assert combo_box.currentText() == "Option 1"
    assert combo_box.currentIndex() == 0

    app.quit()
