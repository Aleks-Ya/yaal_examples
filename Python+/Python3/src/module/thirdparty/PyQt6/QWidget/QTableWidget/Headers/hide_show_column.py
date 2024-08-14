from PyQt6.QtWidgets import QApplication, QTableWidget, QWidget, QPushButton, QVBoxLayout

app: QApplication = QApplication([])
window: QWidget = QWidget()


def __hide_column() -> None:
    table.hideColumn(1)


def __show_column() -> None:
    table.showColumn(1)


table: QTableWidget = QTableWidget(0, 3)
table.setHorizontalHeaderLabels(["Column 1", "Column 2", "Column 3"])
table.insertRow(table.rowCount())
table.insertRow(table.rowCount())

add_button: QPushButton = QPushButton("Hide Column 2")
add_button.clicked.connect(__hide_column)

remove_button: QPushButton = QPushButton("Show Column 2")
remove_button.clicked.connect(__show_column)

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(table)
layout.addWidget(add_button)
layout.addWidget(remove_button)

window.setLayout(layout)
window.setMinimumWidth(500)

window.show()
app.exec()
