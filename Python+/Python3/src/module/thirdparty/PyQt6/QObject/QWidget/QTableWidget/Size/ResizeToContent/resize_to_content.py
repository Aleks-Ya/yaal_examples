from PyQt6.QtWidgets import QApplication, QTableWidget, QHeaderView

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers

app: QApplication = QApplication([])

table: QTableWidget = create_table_with_headers()
table.resizeRowsToContents()
table.resizeColumnsToContents()
table.horizontalHeader().setSectionResizeMode(0, QHeaderView.ResizeMode.Stretch)
table.setSizeAdjustPolicy(QTableWidget.SizeAdjustPolicy.AdjustToContents)
table.window().adjustSize()
table.show()

app.exec()
