from unittest.mock import MagicMock


class ProductionClass:
    def method(self):
        return "abc"


thing = ProductionClass()
thing.method = MagicMock(return_value="xyz")
res = thing.method()
assert res == "xyz"
