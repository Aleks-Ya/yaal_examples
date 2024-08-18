from PyQt6.QtWidgets import QTableWidgetItem, QTableWidget


def create_table_with_headers():
    table: QTableWidget = QTableWidget(3, 3)
    table.setHorizontalHeaderLabels(["Name", "Age", "City"])

    table.setItem(0, 0, QTableWidgetItem("John"))
    table.setItem(0, 1, QTableWidgetItem("40"))
    table.setItem(0, 2, QTableWidgetItem("London Madrid Rome Amsterdam Vienna Stockholm Oslo"))

    table.setItem(1, 0, QTableWidgetItem("Mary"))
    table.setItem(1, 1, QTableWidgetItem("30"))
    table.setItem(1, 2, QTableWidgetItem("Berlin\nBrussels\nPrague"))

    table.setItem(2, 0, QTableWidgetItem("Mark"))
    table.setItem(2, 1, QTableWidgetItem("20"))
    table.setItem(2, 2, QTableWidgetItem("Paris"))

    return table
