from PyQt6.QtWidgets import QApplication, QWidget, QTextEdit, QVBoxLayout

app: QApplication = QApplication([])

label: QTextEdit = QTextEdit()
label.setText("Hello")

layout: QVBoxLayout = QVBoxLayout()
layout.setContentsMargins(100, 100, 100, 100)
layout.addWidget(label)

widget: QWidget = QWidget()
widget.setLayout(layout)
widget.show()

app.exec()
