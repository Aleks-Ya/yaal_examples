from PyQt6.QtWidgets import QStyle, QApplication


def test_get_style(qapp: QApplication):
    app_style: QStyle = qapp.style()
    print(app_style.objectName())
    assert app_style.objectName() == 'fusion'
