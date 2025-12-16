from PyQt6.QtWidgets import QTableWidget, QTableWidgetItem, QVBoxLayout

from tests.module.thirdparty.PyQt6 import window


class LazyLoadingTable(QTableWidget):
    def __init__(self, total_rows: int, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.total_rows: int = total_rows
        self.loaded_rows: int = 0
        self.setRowCount(0)
        self.setColumnCount(3)
        # noinspection PyUnresolvedReferences
        self.verticalScrollBar().valueChanged.connect(self.on_scroll)
        self.load_more_rows()

    def on_scroll(self):
        print("Scrolling")
        if self.verticalScrollBar().value() == self.verticalScrollBar().maximum():
            print("Scroll more")
            self.load_more_rows()

    def load_more_rows(self, chunk_size=50):
        for _ in range(chunk_size):
            if self.loaded_rows >= self.total_rows:
                break
            row_position: int = self.rowCount()
            self.insertRow(row_position)
            for col in range(self.columnCount()):
                self.setItem(row_position, col, QTableWidgetItem(f"Item {self.loaded_rows}, {col}"))
            self.loaded_rows += 1


with window() as window:
    layout: QVBoxLayout = QVBoxLayout()
    table: LazyLoadingTable = LazyLoadingTable(total_rows=1000)
    layout.addWidget(table)
    window.setLayout(layout)
    window.show()
