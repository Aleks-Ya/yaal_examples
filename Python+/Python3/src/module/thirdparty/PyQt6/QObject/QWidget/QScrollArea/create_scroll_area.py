from PyQt6.QtWidgets import QApplication, QLabel, QScrollArea

app: QApplication = QApplication([])

label: QLabel = QLabel('abc ' * 100)

scroll_area: QScrollArea = QScrollArea()
scroll_area.setWidget(label)
scroll_area.show()

app.exec()
