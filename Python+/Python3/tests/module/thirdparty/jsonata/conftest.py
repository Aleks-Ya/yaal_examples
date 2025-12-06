from typing import Any

from jsonata import Jsonata


def evaluate(data: dict[str, Any], expression: str) -> Any:
    return Jsonata(expression).evaluate(data)
