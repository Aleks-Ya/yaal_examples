from PyQt6.QtWidgets import QLabel, QPushButton, QApplication

from tests.module.thirdparty.PyQt6 import vbox

# Reproduce "RuntimeError: wrapped C/C++ object of type QLabel has been deleted"
with vbox() as layout:
    label: QLabel = QLabel("Label")
    layout.addWidget(label)

    button: QPushButton = QPushButton("Reproduce the error")
    button.clicked.connect(lambda: print(label.text()))
    layout.addWidget(button)

    label.deleteLater()
    QApplication.processEvents()
