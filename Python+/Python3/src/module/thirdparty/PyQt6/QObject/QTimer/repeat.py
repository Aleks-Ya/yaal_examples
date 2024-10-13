from PyQt6.QtCore import QTimer
from PyQt6.QtWidgets import QApplication


def test_interval():
    application: QApplication = QApplication([])
    stop: bool = False

    def stop_loop():
        nonlocal stop
        stop = True
        print('STOPPING LOOP')

    timer: QTimer = QTimer()
    timer.setSingleShot(True)
    timer.setInterval(2000)
    timer.timeout.connect(stop_loop)
    timer.start()
    counter: int = 0
    while True:
        counter += 1
        print(counter)
        application.processEvents()
        if stop:
            break
        # do some more work...
        application.thread().msleep(100)
