import logging


def test_logging_function(caplog):
    with caplog.at_level(logging.INFO):
        logging.info("This is a log message")
    assert "This is a log message" in caplog.text
