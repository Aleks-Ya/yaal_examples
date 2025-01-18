import logging
from logging import INFO, DEBUG, Logger


def test_pares_logging_level():
    level_str: str = "DEBUG"
    level_int: int = INFO
    log: Logger = logging.getLogger()
    log.setLevel(level_int)
    assert INFO == log.level
    log.setLevel(level_str)
    assert DEBUG == log.level


def test_get_level_name():
    int_to_str: str = logging.getLevelName(DEBUG)
    str_to_int: int = logging.getLevelName("INFO")
    assert "DEBUG" == int_to_str
    assert 20 == str_to_int
