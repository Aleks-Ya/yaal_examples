from PyQt6.QtCore import QDate


def test_current_date():
    current_date: QDate = QDate.currentDate()
    print(current_date.toString())
