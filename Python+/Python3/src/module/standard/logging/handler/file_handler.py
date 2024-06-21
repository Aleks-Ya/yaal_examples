import logging
import os
import tempfile
from logging import Logger, FileHandler
import unittest


class TestFileHandler(unittest.TestCase):

    def test_log_to_file(self):
        log_file: str = tempfile.mkstemp(".log")[1]
        handler: FileHandler = FileHandler(log_file)
        logger: Logger = logging.getLogger(__name__)
        logger.addHandler(handler)
        logger.error("Logger was configured")
        log_content: str = open(log_file).read()
        self.assertIn("Logger was configured", log_content)

    def test_delete_log_file_during_logger_working(self):
        log_file: str = tempfile.mkstemp(".log")[1]
        handler: FileHandler = FileHandler(log_file)
        logger: Logger = logging.getLogger(__name__)
        logger.addHandler(handler)
        logger.error("Logger was configured 1")
        self.assertTrue(os.path.exists(log_file))
        os.remove(log_file)
        logger.error("Logger was configured 2")
        handler.flush()
        self.assertFalse(os.path.exists(log_file))


if __name__ == '__main__':
    unittest.main()
