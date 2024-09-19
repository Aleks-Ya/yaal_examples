from PyQt6.QtWidgets import QLabel

from src.module.thirdparty.PyQt6 import vbox

with vbox() as layout:
    label_wrap_on: QLabel = QLabel()
    label_wrap_on.setText('Long long long long long long long long long long long long long long long text.')
    label_wrap_on.setWordWrap(True)

    label_wrap_off: QLabel = QLabel()
    label_wrap_off.setText('Long long long long long long long text.')
    label_wrap_off.setWordWrap(False)

    layout.addWidget(label_wrap_on)
    layout.addWidget(label_wrap_off)
