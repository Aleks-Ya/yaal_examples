import unittest


class ByteArrayTestCase(unittest.TestCase):

    def test_empty_byte_array(self):
        empty_byte_array: bytes = bytearray()
        self.assertEqual(type(empty_byte_array).__name__, 'bytearray')
        self.assertEqual(len(empty_byte_array), 0)

    def test_empty_byte_array_from_string(self):
        empty_byte_array_from_string: bytes = b''
        self.assertEqual(type(empty_byte_array_from_string).__name__, 'bytes')
        self.assertEqual(len(empty_byte_array_from_string), 0)

    def test_byte_array_bytes(self):
        byte_array_bytes: bytes = bytes([0x13, 0x00, 0x00, 0x00, 0x08, 0x00])
        self.assertEqual(type(byte_array_bytes).__name__, 'bytes')
        self.assertEqual(len(byte_array_bytes), 6)
        self.assertEqual(bytearray(), b'')
        self.assertEqual(bytes(), b'')
        self.assertEqual(bytearray(), bytes())

    def test_not_empty_byte_array(self):
        key1: bytes = bytes([0x13, 0x00, 0x00, 0x00, 0x08, 0x00])
        key2: bytes = b'\x13\0\0\0\x08\0'
        self.assertEqual(key1, key2)

    def test_byte_array_to_string(self):
        by: bytes = b'abcde'
        string: str = by.decode("utf-8")
        self.assertEqual(string, 'abcde')

    def test_string_to_byte_array(self):
        by: bytes = b'abcde'
        self.assertEqual(len(by), 5)
        s: str = 'abcde'
        by1: bytes = bytes(s, 'utf-8')
        self.assertEqual(by1, b'abcde')
        by2: bytes = s.encode()
        self.assertEqual(by2, b'abcde')
        by3: bytes = s.encode('utf-8')
        self.assertEqual(by3, b'abcde')


if __name__ == '__main__':
    unittest.main()
