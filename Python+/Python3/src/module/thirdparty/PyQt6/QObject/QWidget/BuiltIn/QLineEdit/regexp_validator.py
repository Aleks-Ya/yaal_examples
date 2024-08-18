from PyQt6.QtCore import QRegularExpression
from PyQt6.QtGui import QRegularExpressionValidator
from PyQt6.QtWidgets import QApplication, QLineEdit

app: QApplication = QApplication([])

email_line_edit: QLineEdit = QLineEdit()
email_line_edit.setPlaceholderText("<username>@<domain>.com")
regex: QRegularExpression = QRegularExpression("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[com]{3}\\b",
                                               QRegularExpression.PatternOption.CaseInsensitiveOption)
validator: QRegularExpressionValidator = QRegularExpressionValidator(regex)
email_line_edit.setValidator(validator)
email_line_edit.show()

app.exec()
