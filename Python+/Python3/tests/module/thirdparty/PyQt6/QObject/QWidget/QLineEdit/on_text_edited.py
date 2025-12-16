from PyQt6.QtWidgets import QLineEdit

from tests.module.thirdparty.PyQt6 import app

# Emit only on manual changing (not programmatically)
def __on_text_edited(text: str):
    print(text)


with app():
    line_edit: QLineEdit = QLineEdit()
    # noinspection PyUnresolvedReferences
    line_edit.textEdited.connect(__on_text_edited)
    line_edit.show()
