def test_empty_byte_array() -> None:
    empty_byte_array: bytes = bytearray()
    assert type(empty_byte_array).__name__ == 'bytearray'
    assert len(empty_byte_array) == 0


def test_empty_byte_array_from_string() -> None:
    empty_byte_array_from_string: bytes = b''
    assert type(empty_byte_array_from_string).__name__ == 'bytes'
    assert len(empty_byte_array_from_string) == 0


def test_byte_array_bytes() -> None:
    byte_array_bytes: bytes = bytes([0x13, 0x00, 0x00, 0x00, 0x08, 0x00])
    assert type(byte_array_bytes).__name__ == 'bytes'
    assert len(byte_array_bytes) == 6
    assert bytearray() == b''
    assert bytes() == b''
    assert bytearray() == bytes()


def test_not_empty_byte_array() -> None:
    key1: bytes = bytes([0x13, 0x00, 0x00, 0x00, 0x08, 0x00])
    key2: bytes = b'\x13\0\0\0\x08\0'
    assert key1 == key2


def test_byte_array_to_string() -> None:
    by: bytes = b'abcde'
    string: str = by.decode("utf-8")
    assert string == 'abcde'


def test_string_to_byte_array() -> None:
    by: bytes = b'abcde'
    assert len(by) == 5
    s: str = 'abcde'
    by1: bytes = bytes(s, 'utf-8')
    assert by1 == b'abcde'
    by2: bytes = s.encode()
    assert by2 == b'abcde'
    by3: bytes = s.encode('utf-8')
    assert by3 == b'abcde'
