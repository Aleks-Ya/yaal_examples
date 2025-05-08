import logging
from logging import INFO, DEBUG, Logger


def test_disable_logging():
    logging.disable(INFO)
    # NOT FINISHED
    level_str: str = "DEBUG"
    level_int: int = INFO
    log: Logger = logging.getLogger()
    log.setLevel(level_int)
    assert INFO == log.level
    log.setLevel(level_str)
    assert DEBUG == log.level
