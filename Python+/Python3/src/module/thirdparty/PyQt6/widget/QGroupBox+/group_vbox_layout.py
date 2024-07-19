from typing import Optional

from PyQt6.QtWidgets import QApplication, QCheckBox, QWidget, QVBoxLayout, QGroupBox


class GroupVBox(QGroupBox):
    def __init__(self, title: Optional[str] = None, widgets: Optional[list[QWidget]] = None):
        super().__init__()
        self.layout: QVBoxLayout = QVBoxLayout()
        self.setLayout(self.layout)
        if title:
            self.setTitle(title)
        if widgets:
            for widget in widgets:
                self.layout.addWidget(widget)

    def set_enabled(self, enabled: bool):
        self.setEnabled(enabled)


app: QApplication = QApplication([])

checkbox1: QCheckBox = QCheckBox("Option 1")
checkbox2: QCheckBox = QCheckBox("Option 2")
checkbox3: QCheckBox = QCheckBox("Option 3")

group_box: GroupVBox = GroupVBox('Options', [checkbox1, checkbox2, checkbox3])

group_box.show()

app.exec()
