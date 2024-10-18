from PyQt6.QtWidgets import QVBoxLayout, QApplication, QHBoxLayout


def test_iterate_nested_layouts():
    application: QApplication = QApplication([])

    layout1: QHBoxLayout = QHBoxLayout()
    layout2: QHBoxLayout = QHBoxLayout()

    layout: QVBoxLayout = QVBoxLayout()
    layout.addLayout(layout1)
    layout.addLayout(layout2)

    for child in layout.children():
        assert isinstance(child, QHBoxLayout)
        print(child)

    application.quit()
