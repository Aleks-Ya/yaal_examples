from PyQt6.QtWidgets import QApplication, QLabel, QWidget, QVBoxLayout


def create_widget() -> QWidget:
    widget: QWidget = QWidget()
    layout: QVBoxLayout = QVBoxLayout()
    label: QLabel = QLabel('Hello, World!')
    layout.addWidget(label)
    widget.setLayout(layout)
    widget.show()
    return widget


if __name__ == "__main__":
    app: QApplication = QApplication([])
    widget: QWidget = create_widget()
    app.exec()
