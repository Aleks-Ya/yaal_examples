from abc import ABC, abstractmethod


class Communicator(ABC):
    def __init__(self) -> None:
        self._mark = "!"

    @abstractmethod
    def say(self) -> str:
        ...


class Hello(Communicator):
    def say(self) -> str:
        return f"Hello{self._mark}"


class Buy(Communicator):
    def say(self) -> str:
        return f"Buy{self._mark}"


def test_use_sub_classes():
    hello: Communicator = Hello()
    assert hello.say() == "Hello!"

    buy: Communicator = Buy()
    assert buy.say() == "Buy!"
