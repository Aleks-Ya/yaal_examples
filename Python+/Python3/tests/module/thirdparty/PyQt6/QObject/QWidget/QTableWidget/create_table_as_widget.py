from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QWidget, QVBoxLayout

from tests.module.thirdparty.PyQt6 import app


class MyTableWidget(QWidget):
    def __init__(self):
        super().__init__()
        self.__table = QTableWidget(2, 3)
        self.__layout = QVBoxLayout()
        self.__table.setItem(0, 0, QTableWidgetItem("Item 1"))
        self.__table.setItem(0, 1, QTableWidgetItem("Item 2"))
        self.__table.setItem(0, 2, QTableWidgetItem("Item 3"))
        self.__layout.addWidget(self.__table)
        self.setLayout(self.__layout)


with app():
    table: MyTableWidget = MyTableWidget()
    table.show()
