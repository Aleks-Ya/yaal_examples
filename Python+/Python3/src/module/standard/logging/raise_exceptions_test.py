import logging
from logging import Logger

import pytest


class FaultyHandler(logging.Handler):
    def emit(self, record: logging.LogRecord):
        raise ValueError("Logging error!")


def test_raise_exceptions_enabled():
    logger: Logger = logging.getLogger('test_logger')
    logger.addHandler(FaultyHandler())
    assert logging.raiseExceptions is True  # by default
    with pytest.raises(ValueError, match="Logging error!"):
        logger.error("This will raise an exception")


# NOT WORK
def test_raise_exceptions_disabled():
    logger: Logger = logging.getLogger('test_logger')
    logger.addHandler(FaultyHandler())
    logging.raiseExceptions = False
    logger.error("This will not raise an exception")
