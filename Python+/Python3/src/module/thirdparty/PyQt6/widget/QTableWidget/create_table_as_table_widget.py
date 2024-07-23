from PyQt6.QtWidgets import QApplication, QTableWidget, QTableWidgetItem


class MyTableWidget(QTableWidget):
    def __init__(self):
        super().__init__(2, 3)
        self.setItem(0, 0, QTableWidgetItem("Item 1"))
        self.setItem(0, 1, QTableWidgetItem("Item 2"))
        self.setItem(0, 2, QTableWidgetItem("Item 3"))


app: QApplication = QApplication([])
table: MyTableWidget = MyTableWidget()
table.show()
app.exec()
