from PyQt6.QtCore import QObject, QTimerEvent
from PyQt6.QtWidgets import QApplication


class CustomObject(QObject):
    counter: int = 0

    def timerEvent(self, event: QTimerEvent):
        print(f"Counter: {CustomObject.counter}, Event: {event}")
        CustomObject.counter += 1


def test_start_timer():
    _: QApplication = QApplication([])
    obj: CustomObject = CustomObject()
    timer_id: int = obj.startTimer(500)
    print(f"Timer id: {timer_id}")

    while True:
        QApplication.processEvents()
        if CustomObject.counter > 5:
            break

    obj.killTimer(timer_id)
