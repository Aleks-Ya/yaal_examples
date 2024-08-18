from PyQt6.QtWidgets import QApplication, QTableWidget, QWidget, QPushButton, QVBoxLayout, QMessageBox

app: QApplication = QApplication([])


def __add_column() -> None:
    column_count: int = table.columnCount()
    table.insertColumn(column_count)
    headers: list[str] = [table.horizontalHeaderItem(i).text() if table.horizontalHeaderItem(i) else None
                          for i in range(table.columnCount())]
    headers[column_count] = f"New Column {column_count + 1}"
    table.setHorizontalHeaderLabels(headers)


def __remove_column() -> None:
    current_column: int = table.currentColumn()
    if current_column != -1:
        table.removeColumn(current_column)
    else:
        QMessageBox.warning(None, "Removing columns", "No column is selected")


table: QTableWidget = QTableWidget(0, 3)
table.setHorizontalHeaderLabels(["Column 1", "Column 2", "Column 3"])
table.insertRow(table.rowCount())
table.insertRow(table.rowCount())

add_button: QPushButton = QPushButton("Add Column")
add_button.clicked.connect(__add_column)

remove_button: QPushButton = QPushButton("Remove Column")
remove_button.clicked.connect(__remove_column)

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(table)
layout.addWidget(add_button)
layout.addWidget(remove_button)

window: QWidget = QWidget()
window.setLayout(layout)
window.setMinimumWidth(500)
window.show()

app.exec()
