from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QComboBox, QApplication


class Details:
    def __init__(self, age: int, city: str):
        self.age: int = age
        self.city: str = city

    def __repr__(self):
        return f"Details(age={self.age}, city='self.city')"


def test_add_item():
    app: QApplication = QApplication([])
    combo_box: QComboBox = QComboBox()

    # Set initial data
    item_texts: list[str] = ["John", "Luna"]
    john_details: Details = Details(30, "London")
    luna_details: Details = Details(25, "Berlin")
    item_data: list[Details] = [john_details, luna_details]
    for index in range(len(item_data)):
        combo_box.addItem(item_texts[index], item_data[index])
    assert combo_box.currentData() == john_details

    assert combo_box.itemText(0) == "John"
    assert combo_box.itemData(0) == john_details
    assert combo_box.findData(john_details) == 0
    assert combo_box.findData(john_details, role=Qt.ItemDataRole.UserRole, flags=Qt.MatchFlag.MatchExactly) == 0

    assert combo_box.itemText(1) == "Luna"
    assert combo_box.itemData(1) == luna_details
    assert combo_box.findData(luna_details) == 1

    app.quit()


def test_set_item_data():
    app: QApplication = QApplication([])
    combo_box: QComboBox = QComboBox()

    # Set item texts
    item_texts: list[str] = ["John", "Luna"]
    for index in range(len(item_texts)):
        combo_box.addItem(item_texts[index])

    # Set item datas
    john_details: Details = Details(30, "London")
    luna_details: Details = Details(25, "Berlin")
    item_data: list[Details] = [john_details, luna_details]
    for index in range(len(item_data)):
        combo_box.setItemData(index, item_data[index])
    assert combo_box.currentData() == john_details

    assert combo_box.itemText(0) == "John"
    assert combo_box.itemData(0) == john_details
    assert combo_box.findData(john_details) == 0

    assert combo_box.itemText(1) == "Luna"
    assert combo_box.itemData(1) == luna_details
    assert combo_box.findData(luna_details) == 1

    app.quit()


def test_update_item_data():
    app: QApplication = QApplication([])
    combo_box: QComboBox = QComboBox()

    # Set initial data
    item_texts: list[str] = ["John", "Luna"]
    john_details: Details = Details(30, "London")
    luna_details: Details = Details(25, "Berlin")
    item_data: list[Details] = [john_details, luna_details]
    for index in range(len(item_data)):
        combo_box.addItem(item_texts[index], item_data[index])
    assert combo_box.currentData() == john_details

    assert combo_box.itemText(0) == "John"
    assert combo_box.itemData(0) == john_details
    assert combo_box.findData(john_details) == 0

    assert combo_box.itemText(1) == "Luna"
    assert combo_box.itemData(1) == luna_details
    assert combo_box.findData(luna_details) == 1

    # Update item data
    new_luna_data: Details = Details(25, "Paris")
    combo_box.setItemData(1, new_luna_data)
    assert combo_box.currentData() == john_details
    assert combo_box.itemText(1) == "Luna"
    assert combo_box.itemData(1) == new_luna_data
    assert combo_box.findData(luna_details) == -1
    assert combo_box.findData(new_luna_data) == 1

    # Update item text
    new_john_text: str = "Johnson"
    combo_box.setItemText(0, new_john_text)
    assert combo_box.currentData() == john_details
    assert combo_box.itemText(0) == new_john_text
    assert combo_box.itemData(0) == john_details
    assert combo_box.findData(john_details) == 0

    app.quit()
