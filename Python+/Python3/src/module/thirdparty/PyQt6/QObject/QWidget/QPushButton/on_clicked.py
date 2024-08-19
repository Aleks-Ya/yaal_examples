from PyQt6.QtWidgets import QPushButton


def on_button_click():
    print("Button clicked!")


from src.module.thirdparty.PyQt6 import app

with app():
    button: QPushButton = QPushButton('Click Me')
    button.clicked.connect(on_button_click)
    button.show()
