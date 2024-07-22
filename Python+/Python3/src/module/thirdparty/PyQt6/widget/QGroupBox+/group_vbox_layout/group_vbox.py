from typing import Optional

from PyQt6.QtWidgets import QApplication, QCheckBox, QWidget, QVBoxLayout, QGroupBox, QLayout, QPushButton, QHBoxLayout


class GroupVBox(QGroupBox):
    def __init__(self, title: Optional[str] = None):
        super().__init__()
        self.layout: QVBoxLayout = QVBoxLayout()
        self.setLayout(self.layout)
        if title:
            self.setTitle(title)

    def set_enabled(self, enabled: bool):
        self.setEnabled(enabled)

    def add_widget(self, widget: QWidget):
        self.layout.addWidget(widget)

    def add_layout(self, layout: QLayout):
        self.layout.addLayout(layout)