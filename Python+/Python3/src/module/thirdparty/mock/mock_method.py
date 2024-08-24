from unittest.mock import MagicMock


class ProductionClass:
    def method(self) -> str:
        return "abc"


thing: ProductionClass = ProductionClass()
thing.method = MagicMock(return_value="xyz")
res: str = thing.method()
assert res == "xyz"
