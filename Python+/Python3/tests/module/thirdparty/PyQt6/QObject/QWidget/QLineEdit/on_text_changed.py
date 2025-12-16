from PyQt6.QtWidgets import QLineEdit, QPushButton

from tests.module.thirdparty.PyQt6 import vbox


# Emit only on manual or programmatic changing
def __on_text_changed(text: str):
    print(text)


def __on_button_clicked():
    line_edit.setText(line_edit.text().upper())


with vbox() as vbox:
    push_button: QPushButton = QPushButton("To upper case")
    # noinspection PyUnresolvedReferences
    push_button.clicked.connect(__on_button_clicked)

    line_edit: QLineEdit = QLineEdit()
    # noinspection PyUnresolvedReferences
    line_edit.textChanged.connect(__on_text_changed)

    vbox.addWidget(line_edit)
    vbox.addWidget(push_button)
