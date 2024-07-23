from PyQt6.QtWidgets import QApplication, QTableWidget, QWidget, QPushButton, QVBoxLayout

app: QApplication = QApplication([])
window: QWidget = QWidget()


def __add_row() -> None:
    table_widget.insertRow(table_widget.rowCount())


def __remove_row() -> None:
    current_row: int = table_widget.currentRow()
    if current_row != -1:
        table_widget.removeRow(current_row)


table_widget: QTableWidget = QTableWidget(0, 3)
table_widget.setHorizontalHeaderLabels(["Column 1", "Column 2", "Column 3"])

add_button: QPushButton = QPushButton("Add Row")
add_button.clicked.connect(__add_row)

remove_button: QPushButton = QPushButton("Remove Row")
remove_button.clicked.connect(__remove_row)

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(table_widget)
layout.addWidget(add_button)
layout.addWidget(remove_button)

window.setLayout(layout)

window.show()
app.exec()
