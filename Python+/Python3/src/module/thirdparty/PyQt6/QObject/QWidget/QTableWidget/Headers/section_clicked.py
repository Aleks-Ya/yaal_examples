from PyQt6.QtWidgets import QTableWidget

from src.module.thirdparty.PyQt6.QObject.QWidget.QTableWidget.data import create_table_with_headers
from src.module.thirdparty.PyQt6 import app


def on_event(index: int) -> None:
    print(f"Clicked header: {index}")


with app():
    table: QTableWidget = create_table_with_headers()
    table.horizontalHeader().sectionClicked.connect(on_event)
    table.show()