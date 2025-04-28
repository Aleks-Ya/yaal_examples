import logging

from _pytest.logging import LogCaptureFixture


def test_logging_function(caplog: LogCaptureFixture):
    with caplog.at_level(logging.INFO):
        logging.info("This is a log message")
    assert "This is a log message" in caplog.text
