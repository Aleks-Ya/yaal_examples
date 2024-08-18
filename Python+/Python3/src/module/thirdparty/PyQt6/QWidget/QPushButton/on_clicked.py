from PyQt6.QtWidgets import QApplication, QPushButton


def on_button_click():
    print("Button clicked!")


app: QApplication = QApplication([])

button: QPushButton = QPushButton('Click Me')
button.clicked.connect(on_button_click)
button.show()

app.exec()
