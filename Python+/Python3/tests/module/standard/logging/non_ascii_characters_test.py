import logging
from logging import Logger


def test_raise_exceptions_enabled():
    logger: Logger = logging.getLogger('test_logger')
    logger.error("# coding: ascii\n\N{SNOWMAN}")
    logger.error("A\u2068B\u2069C")
