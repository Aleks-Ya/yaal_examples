from PyQt6.QtCore import QRegularExpression
from PyQt6.QtGui import QRegularExpressionValidator
from PyQt6.QtWidgets import QLineEdit

from tests.module.thirdparty.PyQt6 import app

with app():
    email_line_edit: QLineEdit = QLineEdit()
    email_line_edit.setPlaceholderText("<username>@<domain>.com")
    regex: QRegularExpression = QRegularExpression("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[com]{3}\\b",
                                                   QRegularExpression.PatternOption.CaseInsensitiveOption)
    validator: QRegularExpressionValidator = QRegularExpressionValidator(regex)
    email_line_edit.setValidator(validator)
    email_line_edit.show()
