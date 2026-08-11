import logging
from logging import LogRecord, Formatter

from _pytest.logging import LogCaptureFixture


def test_logging_function(caplog: LogCaptureFixture):
    with caplog.at_level(logging.INFO):
        logging.info("This is a log message")
    assert "This is a log message" in caplog.text


# NOT WORK
def test_format(caplog: LogCaptureFixture):
    format_str: str = '%(levelname)-8s MY-FORMAT [%(threadName)-10s] %(message)s'
    formatter_obj: Formatter = logging.Formatter(format_str)
    caplog.handler.setFormatter(formatter_obj)
    logging.basicConfig(format=format_str)
    with caplog.at_level(logging.INFO):
        logging.info("A message")
    record: LogRecord = caplog.records[0]
    formatter: Formatter = logging.root.handlers[0].formatter
    formatted: str = formatter.format(record)

    assert formatted == "INFO - myapp - hello"
