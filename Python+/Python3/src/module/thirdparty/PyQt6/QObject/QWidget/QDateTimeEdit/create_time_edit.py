from PyQt6.QtCore import QDateTime
from PyQt6.QtWidgets import QDateTimeEdit

from src.module.thirdparty.PyQt6 import app

with app():
    time_edit: QDateTimeEdit = QDateTimeEdit()
    time_edit.setDateTime(QDateTime.currentDateTime())
    time_edit.setDisplayFormat("yyyy-MM-dd hh:mm:ss")
    time_edit.show()
