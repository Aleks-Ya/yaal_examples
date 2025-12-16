from PyQt6.QtWidgets import QComboBox

from tests.module.thirdparty.PyQt6 import app


class Animal:
    def __init__(self, name: str, owner: str):
        self.name: str = name
        self.owner: str = owner

    def __repr__(self):
        return f"Animal(name='{self.name}', owner='{self.owner}')"


def __on_current_index_changed(index: int):
    print(f"Selected index: {index}")
    current_data: Animal = combo_box.currentData()
    print(f"Selected animal: {current_data}")


with app():
    animals: list[Animal] = [Animal("Tom", "John"), Animal("Luna", "Jane")]
    combo_box: QComboBox = QComboBox()
    # noinspection PyUnresolvedReferences
    combo_box.currentIndexChanged.connect(__on_current_index_changed)
    for animal in animals:
        combo_box.addItem(animal.name, animal)
    combo_box.show()
