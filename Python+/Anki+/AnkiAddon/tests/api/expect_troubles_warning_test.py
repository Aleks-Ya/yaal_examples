from PyQt6.QtWidgets import QApplication
from PyQt6.QtWebEngineWidgets import QWebEngineView


# Reproduce "Release of profile requested but WebEnginePage still not deleted. Expect troubles !" warning in Tox
# NOT WORKING
def test_webengine_warning(qtbot):
    app = QApplication.instance() or QApplication([])
    view = QWebEngineView()
    # Do not explicitly delete 'view' or ensure proper cleanup
    # The test ends and the QApplication exits, releasing the profile too early
