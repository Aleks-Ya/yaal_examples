import unittest


class TestAssertObjects(unittest.TestCase):

    def test_equals(self):
        person1: Person = Person("John", 30)
        person2: Person = Person("John", 25)
        person3: Person = Person("John", 30)
        self.assertNotEqual(person1, person2)
        self.assertEqual(person1, person3)


class Person:
    def __init__(self, name: str, age: int):
        self.__name: str = name
        self.__age: int = age

    def get_name(self) -> str:
        return self.__name

    def get_age(self) -> int:
        return self.__age

    def __eq__(self, other):
        if isinstance(other, Person):
            return self.__name == other.get_name() and self.__age == other.get_age()
        return False

    def __repr__(self):
        return f"Person(name={self.__name}, age={self.__age})"


if __name__ == '__main__':
    unittest.main()
