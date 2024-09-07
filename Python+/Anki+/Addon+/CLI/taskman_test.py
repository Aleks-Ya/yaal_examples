import threading
from unittest.mock import MagicMock

from aqt import AnkiQt
from aqt.taskman import TaskManager

ran_on_main: bool = False
ran_in_background: bool = False


def test_run_on_main():
    mw: AnkiQt = MagicMock()
    taskman: TaskManager = TaskManager(mw)
    assert threading.current_thread().name == "MainThread"
    assert not ran_on_main
    taskman.run_on_main(__ran_on_main)
    assert ran_on_main


def test_run_in_background():
    mw: AnkiQt = MagicMock()
    taskman: TaskManager = TaskManager(mw)
    assert threading.current_thread().name == "MainThread"
    assert not ran_in_background
    taskman.run_in_background(__ran_in_background)
    assert ran_in_background


def __ran_on_main() -> None:
    assert threading.current_thread().name == "MainThread"
    global ran_on_main
    ran_on_main = True


def __ran_in_background() -> None:
    assert threading.current_thread().name != "MainThread"
    global ran_in_background
    ran_in_background = True
