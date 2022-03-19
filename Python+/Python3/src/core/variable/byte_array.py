# Byte array

# Empty byte array
empty_byte_array: bytes = bytearray()
assert type(empty_byte_array).__name__ == 'bytearray'
assert len(empty_byte_array) == 0

empty_byte_array_from_string: bytes = b''
assert type(empty_byte_array_from_string).__name__ == 'bytes'
assert len(empty_byte_array_from_string) == 0

byte_array_bytes: bytes = bytes([0x13, 0x00, 0x00, 0x00, 0x08, 0x00])
assert type(byte_array_bytes).__name__ == 'bytes'
assert len(byte_array_bytes) == 6

assert bytearray() == b''
assert bytes() == b''
assert bytearray() == bytes()

# Not empty byte array
key1: bytes = bytes([0x13, 0x00, 0x00, 0x00, 0x08, 0x00])
key2: bytes = b'\x13\0\0\0\x08\0'
assert key1 == key2

# Byte array to string
by: bytes = b'abcde'
string: str = by.decode("utf-8")
assert string == 'abcde'

# String to byte array (literal)
by: bytes = b'abcde'
assert len(by) == 5

# String to byte array (variable)
s: str = 'abcde'
by1: bytes = bytes(s, 'utf-8')
assert by1 == b'abcde'
by2: bytes = s.encode()
assert by2 == b'abcde'
by3: bytes = s.encode('utf-8')
assert by3 == b'abcde'
