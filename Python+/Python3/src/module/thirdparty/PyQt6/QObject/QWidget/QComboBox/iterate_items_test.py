from PyQt6.QtWidgets import QComboBox, QApplication


def test_iterate_items():
    app: QApplication = QApplication([])
    combo_box: QComboBox = QComboBox()
    exp_items: list[str] = ["Option 1", "Option 2", "Option 3"]
    combo_box.addItems(exp_items)

    act_items: list[str] = []
    for i in range(combo_box.count()):
        act_items.append(combo_box.itemText(i))

    assert act_items == exp_items
    app.quit()
