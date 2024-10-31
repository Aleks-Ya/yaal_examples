from PyQt6.QtWidgets import QComboBox, QApplication


class Details:
    def __init__(self, age: int, city: str):
        self.age: int = age
        self.city: str = city

    def __repr__(self):
        return f"Details(age={self.age}, city='self.city')"


def test_user_data():
    app: QApplication = QApplication([])
    combo_box: QComboBox = QComboBox()

    # Set initial item texts
    item_texts: list[str] = ["John", "Luna"]
    for index in range(len(item_texts)):
        combo_box.addItem(item_texts[index])

    # Set initial item data
    john_details: Details = Details(30, "London")
    luna_details: Details = Details(25, "Berlin")
    item_data: list[Details] = [john_details, luna_details]
    for index in range(len(item_data)):
        combo_box.setItemData(index, item_data[index])

    assert combo_box.itemText(0) == "John"
    assert combo_box.itemData(0) == john_details

    assert combo_box.itemText(1) == "Luna"
    assert combo_box.itemData(1) == luna_details

    # Update item data
    new_luna_data: Details = Details(25, "Paris")
    combo_box.setItemData(1, new_luna_data)
    assert combo_box.itemText(1) == "Luna"
    assert combo_box.itemData(1) == new_luna_data

    # Update item text
    new_john_text: str = "Johnson"
    combo_box.setItemText(0, new_john_text)
    assert combo_box.itemText(0) == new_john_text
    assert combo_box.itemData(0) == john_details

    app.quit()
