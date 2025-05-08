import pytest


@pytest.mark.parametrize("size_in_bytes,precision,size_str", [
    (5, 0, '5 B'),
    (5, 1, '5 B'),
    (5, 3, '5 B'),
    (20, 0, '20 B'),
    (20, 1, '20 B'),
    (20, 3, '20 B'),
    (456, 0, '456 B'),
    (456, 1, '456 B'),
    (456, 3, '456 B'),

    (1024, 0, '1 KB'),
    (1024, 1, '1.0 KB'),
    (1024, 3, '1.000 KB'),
    (45_654, 0, '45 KB'),
    (45_654, 1, '44.6 KB'),
    (45_654, 3, '44.584 KB'),
    (987_654, 0, '965 KB'),
    (987_654, 1, '964.5 KB'),
    (987_654, 3, '964.506 KB'),

    (1_234_567, 0, '1 MB'),
    (1_234_567, 1, '1.2 MB'),
    (1_234_567, 3, '1.177 MB'),

    (1_123_234_567, 0, '1 GB'),
    (1_123_234_567, 1, '1.0 GB'),
    (1_123_234_567, 3, '1.046 GB'),
    (1_567_123_234_567, 0, '1459 GB'),
    (1_567_123_234_567, 1, '1459.5 GB'),
    (1_567_123_234_567, 3, '1459.497 GB'),
])
def test_bytes_to_str_with_precision(size_in_bytes: int, precision: int, size_str: str):
    assert __bytes_to_str_with_precision(size_in_bytes, precision) == size_str


@pytest.mark.parametrize("size_in_bytes,max_precision,size_str", [
    (5, 0, '5 B'),
    (5, 1, '5 B'),
    (5, 3, '5 B'),
    (20, 0, '20 B'),
    (20, 1, '20 B'),
    (20, 3, '20 B'),
    (456, 0, '456 B'),
    (456, 1, '456 B'),
    (456, 3, '456 B'),

    (1024, 0, '1 KB'),
    (1024, 1, '1.0 KB'),
    (1024, 3, '1.000 KB'),
    (45_654, 0, '45 KB'),
    (45_654, 1, '45 KB'),
    (45_654, 3, '44.58 KB'),
    (987_654, 0, '965 KB'),
    (987_654, 1, '965 KB'),
    (987_654, 3, '964.5 KB'),

    (1_234_567, 0, '1 MB'),
    (1_234_567, 1, '1.2 MB'),
    (1_234_567, 3, '1.177 MB'),

    (1_123_234_567, 0, '1 GB'),
    (1_123_234_567, 1, '1.0 GB'),
    (1_123_234_567, 3, '1.046 GB'),
    (1_567_123_234_567, 0, '1459 GB'),
    (1_567_123_234_567, 1, '1459 GB'),
    (1_567_123_234_567, 3, '1459 GB'),
])
def test_bytes_to_str_with_max_precision(size_in_bytes: int, max_precision: int, size_str: str):
    assert __bytes_to_str_with_max_precision(size_in_bytes, max_precision) == size_str


@pytest.mark.parametrize("size_in_bytes,significant_digits,size_str", [
    (5, 0, '5 B'),
    (5, 1, '5 B'),
    (5, 3, '5 B'),
    (20, 0, '20 B'),
    (20, 1, '20 B'),
    (20, 3, '20 B'),
    (456, 0, '456 B'),
    (456, 1, '456 B'),
    (456, 3, '456 B'),

    (1024, 0, '1 KB'),
    (1024, 1, '1 KB'),
    (1024, 3, '1.00 KB'),
    (45_654, 0, '45 KB'),
    (45_654, 1, '45 KB'),
    (45_654, 3, '44.6 KB'),
    (987_654, 0, '965 KB'),
    (987_654, 1, '965 KB'),
    (987_654, 3, '965 KB'),

    (1_234_567, 0, '1 MB'),
    (1_234_567, 1, '1 MB'),
    (1_234_567, 3, '1.18 MB'),

    (1_123_234_567, 0, '1 GB'),
    (1_123_234_567, 1, '1 GB'),
    (1_123_234_567, 3, '1.05 GB'),
    (1_567_123_234_567, 0, '1459 GB'),
    (1_567_123_234_567, 1, '1459 GB'),
    (1_567_123_234_567, 3, '1459 GB'),
])
def test_bytes_to_str_with_significant_digits(size_in_bytes: int, significant_digits: int, size_str: str):
    assert __bytes_to_str_with_significant_digits(size_in_bytes, significant_digits) == size_str


def __bytes_to_str_with_precision(size_in_bytes: int, precision: int, unit_separator: str = " ") -> str:
    divisor: int = 1024
    byte_unit: str = 'B'
    units: list[str] = [byte_unit, 'KB', 'MB', 'GB']
    size: float = size_in_bytes
    unit_index: int = 0
    while size >= divisor and unit_index < len(units) - 1:
        size /= divisor
        unit_index += 1
    unit: str = units[unit_index]
    precision: int = precision if unit != byte_unit else 0
    return f'{size:0.{precision}f}{unit_separator}{unit}'


def __bytes_to_str_with_max_precision(size_in_bytes: int, max_precision: int, unit_separator: str = " ") -> str:
    divisor: int = 1024
    byte_unit: str = 'B'
    units: list[str] = [byte_unit, 'KB', 'MB', 'GB']
    size: float = size_in_bytes
    unit_index: int = 0
    while size >= divisor and unit_index < len(units) - 1:
        size /= divisor
        unit_index += 1
    unit: str = units[unit_index]
    precision: int = max(0, max_precision - len(str(int(size))) + 1) if unit != byte_unit else 0
    return f'{size:0.{precision}f}{unit_separator}{unit}'


def __bytes_to_str_with_significant_digits(size_in_bytes: int, significant_digits: int = 3,
                                           unit_separator: str = " ") -> str:
    divisor: int = 1024
    byte_unit: str = 'B'
    units: list[str] = [byte_unit, 'KB', 'MB', 'GB']
    size: float = size_in_bytes
    unit_index: int = 0
    while size >= divisor and unit_index < len(units) - 1:
        size /= divisor
        unit_index += 1
    unit: str = units[unit_index]
    precision: int = max(0, significant_digits - len(str(int(size)))) if unit != byte_unit else 0
    return f"{size:.{precision}f}{unit_separator}{unit}"
