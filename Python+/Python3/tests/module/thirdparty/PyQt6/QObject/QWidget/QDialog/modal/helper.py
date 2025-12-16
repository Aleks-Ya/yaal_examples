from typing import Optional

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QDialog, QVBoxLayout, QLabel, QPushButton, QApplication, QWidget


def create_modal_dialog(parent: QWidget, is_modal: Optional[bool],
                        window_modality: Optional[Qt.WindowModality]) -> QDialog:
    dialog: QDialog = QDialog(parent)
    dialog.resize(350, 150)
    dialog.setWindowTitle("The dialog window")

    assert not dialog.isModal()
    if is_modal:
        dialog.setModal(is_modal)  # Invokes "dialog.setWindowModality(Qt.WindowModality.ApplicationModal)"
        assert dialog.isModal() == is_modal

    if dialog.isModal():
        assert dialog.windowModality() == Qt.WindowModality.ApplicationModal
    else:
        assert dialog.windowModality() == Qt.WindowModality.NonModal
    if window_modality:
        dialog.setWindowModality(window_modality)
        assert dialog.windowModality() == window_modality

    label: QLabel = QLabel(f"This is a MODAL dialog.\n"
                           f"Is modal: {dialog.isModal()}\n"
                           f"Window modality: {dialog.windowModality()}")

    button: QPushButton = QPushButton("Close")
    button.clicked.connect(dialog.close)

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(label)
    layout.addWidget(button)

    dialog.setLayout(layout)
    dialog.finished.connect(QApplication.quit)
    return dialog
