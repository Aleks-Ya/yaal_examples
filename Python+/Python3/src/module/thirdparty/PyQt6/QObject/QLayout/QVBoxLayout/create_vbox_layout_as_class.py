from PyQt6.QtWidgets import QApplication, QWidget, QVBoxLayout, QPushButton


class LayoutWithButtons(QVBoxLayout):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        button1: QPushButton = QPushButton('Button 1')
        button2: QPushButton = QPushButton('Button 2')
        self.addWidget(button1)
        self.addWidget(button2)


app: QApplication = QApplication([])

layout: LayoutWithButtons = LayoutWithButtons()

window: QWidget = QWidget()
window.setLayout(layout)
window.show()

app.exec()
