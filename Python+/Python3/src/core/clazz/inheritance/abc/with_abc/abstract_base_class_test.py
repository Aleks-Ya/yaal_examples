from abc import ABC, abstractmethod


class Communicator(ABC):
    @abstractmethod
    def say(self) -> str:
        ...


class Hello(Communicator):
    def say(self) -> str:
        return "Hello!"


class Buy(Communicator):
    def say(self) -> str:
        return "Buy!"


def test_use_sub_classes():
    hello: Communicator = Hello()
    assert hello.say() == "Hello!"

    buy: Communicator = Buy()
    assert buy.say() == "Buy!"
