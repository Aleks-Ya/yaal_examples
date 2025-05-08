from PyQt6.QtWidgets import QLabel


def test_destroyed_signal(qtbot):
    label: QLabel = QLabel('Hello')
    qtbot.addWidget(label)
    with qtbot.waitSignal(label.destroyed, timeout=1000) as signal_triggered:
        label.deleteLater()
    assert signal_triggered.signal_triggered
