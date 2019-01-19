# Byte array

# Empty byte array
empty_byte_array = bytearray()
assert type(empty_byte_array).__name__ == 'bytearray'
assert len(empty_byte_array) == 0

empty_byte_array_from_string = b''
assert type(empty_byte_array_from_string).__name__ == 'bytes'
assert len(empty_byte_array_from_string) == 0

byte_array_bytes = bytes([0x13, 0x00, 0x00, 0x00, 0x08, 0x00])
assert type(byte_array_bytes).__name__ == 'bytes'
assert len(byte_array_bytes) == 6

assert bytearray() == b''
assert bytes() == b''
assert bytearray() == bytes()

# Not empty byte array
key1 = bytes([0x13, 0x00, 0x00, 0x00, 0x08, 0x00])
key2 = b'\x13\0\0\0\x08\0'
assert key1 == key2

# Byte array to string
by = b'abcde'
string = by.decode("utf-8")
assert string == 'abcde'

# String to byte array
by = b'abcde'
assert len(by) == 5

