import dataclasses
import json
from dataclasses import dataclass
from typing import Optional


@dataclass
class Person:
    name: str
    age: Optional[int]


def test_serialize():
    people: list[Person] = [Person("John", 30), Person("Mary", None)]
    serialized_people: list[dict[str, str]] = [dataclasses.asdict(person) for person in people]
    json_str: str = json.dumps(serialized_people)
    assert json_str == '[{"name": "John", "age": 30}, {"name": "Mary", "age": null}]'
