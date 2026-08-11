from PyQt6.QtWidgets import QApplication


def test_get_application(qapp: QApplication):
    assert qapp
