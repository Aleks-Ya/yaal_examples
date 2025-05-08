import logging
import os
import tempfile
from logging import Logger, FileHandler


def test_log_to_file():
    log_file: str = tempfile.mkstemp(".log")[1]
    handler: FileHandler = FileHandler(log_file)
    logger: Logger = logging.getLogger(__name__)
    logger.addHandler(handler)
    logger.error("Logger was configured")
    log_content: str = open(log_file).read()
    assert "Logger was configured" in log_content


def test_delete_log_file_during_logger_working():
    log_file: str = tempfile.mkstemp(".log")[1]
    handler: FileHandler = FileHandler(log_file)
    logger: Logger = logging.getLogger(__name__)
    logger.addHandler(handler)
    logger.error("Logger was configured 1")
    assert os.path.exists(log_file)
    os.remove(log_file)
    logger.error("Logger was configured 2")
    handler.flush()
    assert not os.path.exists(log_file)
