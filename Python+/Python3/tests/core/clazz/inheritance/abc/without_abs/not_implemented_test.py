from abc import abstractmethod


class Communicator:
    @abstractmethod
    def say(self) -> str:
        ...

    @abstractmethod
    def think(self) -> str:
        ...


# noinspection PyAbstractClass
class Hello(Communicator):
    def say(self) -> str:
        return "Hello!"


def test_ignore_not_implemented_function():
    hello: Communicator = Hello()
    assert hello.say() == "Hello!"
