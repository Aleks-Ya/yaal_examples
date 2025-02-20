# UnicodeEncodeError: 'ascii' codec can't encode character '\u2068' in position 9: ordinal not in range(128)

import tempfile
from logging import getLogger
from logging import Logger, FileHandler

import pytest


@pytest.fixture
def log() -> Logger:
    return getLogger()


@pytest.fixture
def filename() -> str:
    _, full_name = tempfile.mkstemp()
    print(f"Output file: {full_name}")
    return full_name


def test_reproduce(log: Logger, filename: str):
    handler: FileHandler = FileHandler(filename, encoding="ascii")
    log.addHandler(handler)
    log.error("Unicode: \u2068 ⁨")


def test_fix_by_encoding(log: Logger, filename: str):
    handler: FileHandler = FileHandler(filename, encoding="utf-8")
    log.addHandler(handler)
    log.error("Unicode: \u2068 ⁨")


def test_fix_by_errors(log: Logger, filename: str):
    handler: FileHandler = FileHandler(filename, encoding="ascii", errors="replace")
    log.addHandler(handler)
    log.error("Unicode: \u2068 ⁨")


def test_fix_by_encoding_and_errors(log: Logger, filename: str):
    handler: FileHandler = FileHandler(filename, encoding="utf-8", errors="replace")
    log.addHandler(handler)
    log.error("Unicode: \u2068 ⁨")
