from PyQt6.QtCore import QTimer
from PyQt6.QtWidgets import QApplication


def test_single_shot():
    application: QApplication = QApplication([])
    finished: bool = False

    def on_timeout():
        nonlocal finished
        finished = True
        print('Finished!')

    timer: QTimer = QTimer()
    timer.setSingleShot(True)
    timer.setInterval(1000)
    # noinspection PyUnresolvedReferences
    timer.timeout.connect(on_timeout)
    timer.start()
    counter: int = 0
    while True:
        counter += 1
        print(counter)
        application.processEvents()
        if finished:
            break
        # do some more work...
        application.thread().msleep(100)
