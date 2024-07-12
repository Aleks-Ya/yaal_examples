import unittest

from pint import UnitRegistry
from pint.registry import Quantity


class TestPint(unittest.TestCase):

    def test_srt_to_bytes(self):
        registry: UnitRegistry = UnitRegistry()
        size: Quantity = registry.Quantity("100")
        size_in_bytes: int = size.to('bytes').magnitude
        self.assertEqual(12.5, size_in_bytes)


if __name__ == '__main__':
    unittest.main()
