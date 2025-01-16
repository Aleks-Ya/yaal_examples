from typing import Callable


class MyCallable(Callable[[None], None]):
    def __init__(self):
        self.text: str = "A"

    def __call__(self, *args, **kwargs):
        self.text = "B"


def test_execute_callable():
    callable: MyCallable = MyCallable()
    assert callable.text == "A"
    callable()
    assert callable.text == "B"
