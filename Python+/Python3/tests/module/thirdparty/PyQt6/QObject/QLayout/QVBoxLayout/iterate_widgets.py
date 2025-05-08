from PyQt6.QtWidgets import QVBoxLayout, QPushButton, QLayoutItem, QWidget, QApplication


def test_iterate_layout_widgets():
    application: QApplication = QApplication([])
    button1: QPushButton = QPushButton('Button 1')
    button2: QPushButton = QPushButton('Button 2')

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(button1)
    layout.addWidget(button2)

    for index in range(layout.count()):
        item: QLayoutItem = layout.itemAt(index)
        widget: QWidget = item.widget()
        assert isinstance(widget, QPushButton)
        print(widget)

    application.quit()
