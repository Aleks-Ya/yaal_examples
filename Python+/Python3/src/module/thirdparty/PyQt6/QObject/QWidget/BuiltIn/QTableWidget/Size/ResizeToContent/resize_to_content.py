from PyQt6.QtWidgets import QApplication, QTableWidget

from src.module.thirdparty.PyQt6.QWidget.QTableWidget.data import create_table_with_headers

app: QApplication = QApplication([])

table: QTableWidget = create_table_with_headers()
table.resizeRowsToContents()
table.resizeColumnsToContents()
table.show()

app.exec()
