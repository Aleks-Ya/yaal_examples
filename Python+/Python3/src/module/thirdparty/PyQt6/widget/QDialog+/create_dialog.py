from PyQt6.QtWidgets import QApplication, QDialog, QVBoxLayout, QLabel, QPushButton

app: QApplication = QApplication([])

dialog: QDialog = QDialog()
dialog.setWindowTitle("Create Dialog")

label: QLabel = QLabel("This is a dialog")

button: QPushButton = QPushButton("Close")
button.clicked.connect(dialog.close)

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(label)
layout.addWidget(button)

dialog.setLayout(layout)
dialog.finished.connect(app.quit)

dialog.show()
app.exec()
