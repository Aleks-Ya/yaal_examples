from abc import ABC, abstractmethod

from pytest import raises


class Communicator(ABC):
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


def test_cannot_instantiate():
    with raises(TypeError,
                match="Can't instantiate abstract class Hello without an implementation for abstract method 'think'"):
        Hello()
