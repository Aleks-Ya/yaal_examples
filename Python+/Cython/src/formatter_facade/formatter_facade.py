from src.formatter.numbers import Numbers
from src.formatter.strings import upper_case


def prepare_text(s: str) -> str:
    return upper_case(s)


def multiply_numbers(a: int, b: int) -> int:
    numbers: Numbers = Numbers()
    return numbers.multiply(a, b)
