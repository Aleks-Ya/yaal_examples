from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QPushButton

from src.module.thirdparty.PyQt6 import vbox


class ItemCache:
    def __init__(self):
        self.__cache: dict[int, dict[int, QTableWidgetItem]] = {}

    def get_item(self, row, column, text) -> QTableWidgetItem:
        if row in self.__cache:
            column_cache: dict[int, QTableWidgetItem] = self.__cache[row]
            if column in column_cache:
                cached_item: QTableWidgetItem = column_cache[column]
                print(f"Found cached item: {cached_item.text()}")
                return cached_item
        item: QTableWidgetItem = QTableWidgetItem(text)
        if row not in self.__cache:
            self.__cache[row] = {}
        self.__cache[row][column] = item
        print(f"Created new item: {item.text()}")
        return item


item_cache: ItemCache = ItemCache()


def fill():
    table.setItem(0, 0, item_cache.get_item(0, 0, "Item 1"))
    table.setItem(0, 1, item_cache.get_item(0, 1, "Item 2"))


def clear():
    table.takeItem(0, 0)
    table.takeItem(0, 1)
    table.clearContents()


with vbox() as layout:
    rows: int = 2
    columns: int = 1
    table: QTableWidget = QTableWidget(rows, columns)

    clear_button: QPushButton = QPushButton("Clear")
    # noinspection PyUnresolvedReferences
    clear_button.clicked.connect(clear)

    fill_button: QPushButton = QPushButton("Fill")
    # noinspection PyUnresolvedReferences
    fill_button.clicked.connect(fill)

    layout.addWidget(table)
    layout.addWidget(clear_button)
    layout.addWidget(fill_button)
