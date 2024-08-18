from PyQt6.QtCore import QDate

current_date: QDate = QDate.currentDate()
print(current_date.toString())
