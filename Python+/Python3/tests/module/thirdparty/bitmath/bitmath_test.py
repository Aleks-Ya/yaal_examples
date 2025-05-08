import bitmath
from bitmath import Bit


def test_parse_size():
    size: Bit = bitmath.parse_string("100 KiB")
    size_bytes: int = size.to_Byte().value
    assert size_bytes == 102400
