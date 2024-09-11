from abc import ABC, abstractmethod


class Communicator(ABC):
    def __init__(self) -> None:
        self._mark = "!"

    @abstractmethod
    def say(self) -> str:
        pass


class Hello(Communicator):
    def say(self) -> str:
        return f"Hello{self._mark}"


class Buy(Communicator):
    def say(self) -> str:
        return f"Buy{self._mark}"


hello: Communicator = Hello()
assert hello.say() == "Hello!"

buy: Communicator = Buy()
assert buy.say() == "Buy!"
