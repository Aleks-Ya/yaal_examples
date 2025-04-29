from pint import UnitRegistry
from pint.registry import Quantity


def test_srt_to_bytes():
    registry: UnitRegistry = UnitRegistry()
    size: Quantity = registry.Quantity("100")
    size_in_bytes: int = size.to('bytes').magnitude
    assert size_in_bytes == 12.5
