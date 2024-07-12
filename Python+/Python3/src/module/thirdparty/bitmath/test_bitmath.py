import unittest

import bitmath
from bitmath import Bit, Byte


class TestBitMath(unittest.TestCase):

    def test_parse_size(self):
        size: Bit | Byte = bitmath.parse_string("100 KiB")
        size_bytes: int = size.to_Byte().value
        self.assertEqual(102400, size_bytes)


if __name__ == '__main__':
    unittest.main()
