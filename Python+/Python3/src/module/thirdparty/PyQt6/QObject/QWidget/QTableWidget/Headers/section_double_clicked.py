from PyQt6.QtWidgets import QApplication, QTableWidget

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers

app: QApplication = QApplication([])


def on_event(index: int) -> None:
    print(f"Double clicked header: {index}")


table: QTableWidget = create_table_with_headers()
table.horizontalHeader().sectionDoubleClicked.connect(on_event)
table.show()

app.exec()
