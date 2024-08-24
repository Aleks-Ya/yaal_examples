from unittest.mock import MagicMock


class ProductionClass:

    def __init__(self):
        super().__init__()
        self.person: str = "John"


thing: ProductionClass = MagicMock()
thing.person = "Mary"
assert thing.person == "Mary"
