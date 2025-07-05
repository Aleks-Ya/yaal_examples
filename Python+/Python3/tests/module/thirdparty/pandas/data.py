from dataclasses import dataclass


@dataclass
class Person:
    name: str
    age: int

    def __str__(self):
        return f"{self.name}-{self.age}"