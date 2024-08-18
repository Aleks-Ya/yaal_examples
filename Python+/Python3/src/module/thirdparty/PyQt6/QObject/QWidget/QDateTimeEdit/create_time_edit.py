from PyQt6.QtCore import QDateTime
from PyQt6.QtWidgets import QApplication, QDateTimeEdit

app: QApplication = QApplication([])

time_edit: QDateTimeEdit = QDateTimeEdit()
time_edit.setDateTime(QDateTime.currentDateTime())
time_edit.setDisplayFormat("yyyy-MM-dd hh:mm:ss")
time_edit.show()

app.exec()
