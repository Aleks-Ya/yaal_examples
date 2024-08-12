from PyQt6.QtWidgets import QApplication, QTableWidget, QWidget, QPushButton, QVBoxLayout

app: QApplication = QApplication([])
window: QWidget = QWidget()


def __hide_row() -> None:
    table.hideRow(1)


def __show_row() -> None:
    table.showRow(1)


table: QTableWidget = QTableWidget(0, 3)
table.setHorizontalHeaderLabels(["Column 1", "Column 2", "Column 3"])
table.insertRow(table.rowCount())
table.insertRow(table.rowCount())
table.insertRow(table.rowCount())

add_button: QPushButton = QPushButton("Hide Row 2")
add_button.clicked.connect(__hide_row)

remove_button: QPushButton = QPushButton("Show Row 2")
remove_button.clicked.connect(__show_row)

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(table)
layout.addWidget(add_button)
layout.addWidget(remove_button)

window.setLayout(layout)
window.setMinimumWidth(500)

window.show()
app.exec()
