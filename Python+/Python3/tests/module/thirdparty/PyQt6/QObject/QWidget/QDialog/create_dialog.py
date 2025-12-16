from PyQt6.QtWidgets import QDialog, QVBoxLayout, QLabel, QPushButton, QApplication

from tests.module.thirdparty.PyQt6 import app

with app():
    dialog: QDialog = QDialog()
    dialog.setWindowTitle("Create Dialog")

    label: QLabel = QLabel("This is a dialog")

    button: QPushButton = QPushButton("Close")
    # noinspection PyUnresolvedReferences
    button.clicked.connect(dialog.close)

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(label)
    layout.addWidget(button)

    dialog.setLayout(layout)
    # noinspection PyUnresolvedReferences
    dialog.finished.connect(QApplication.quit)

    assert dialog.parent() is None
    assert dialog.parentWidget() is None
    assert dialog.nativeParentWidget() is None
    assert layout.parent() == dialog
    assert layout.parentWidget() == dialog
    assert label.parent() == dialog
    assert label.parentWidget() == dialog
    assert label.nativeParentWidget() is None
    assert button.parent() == dialog
    assert button.parentWidget() == dialog
    assert button.nativeParentWidget() is None

    dialog.show()
