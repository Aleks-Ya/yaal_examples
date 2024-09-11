from abc import ABC, abstractmethod


class Communicator(ABC):
    @abstractmethod
    def say(self) -> str:
        pass


class Hello(Communicator):
    def say(self) -> str:
        return "Hello!"


class Buy(Communicator):
    def say(self) -> str:
        return "Buy!"


hello: Communicator = Hello()
assert hello.say() == "Hello!"

buy: Communicator = Buy()
assert buy.say() == "Buy!"
