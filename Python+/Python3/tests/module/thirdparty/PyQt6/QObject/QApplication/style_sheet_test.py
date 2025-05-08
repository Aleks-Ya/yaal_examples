from PyQt6.QtWidgets import QApplication


def test_get_style_sheet(qapp: QApplication):
    style_sheet: str = qapp.styleSheet()
    assert style_sheet == ""
