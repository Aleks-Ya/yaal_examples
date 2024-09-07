import threading
from threading import Thread


def test_get_current_thread():
    thread: Thread = threading.current_thread()
    name: str = thread.name
    assert name == "MainThread"
