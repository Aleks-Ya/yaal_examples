import logging
import unittest
from logging import INFO, DEBUG, Logger


class TestParseLevel(unittest.TestCase):

    def test_disable_logging(self):
        logging.disable(INFO)
        # NOT FINISHED
        level_str: str = "DEBUG"
        level_int: int = INFO
        log: Logger = logging.getLogger()
        log.setLevel(level_int)
        self.assertEqual(INFO, log.level)
        log.setLevel(level_str)
        self.assertEqual(DEBUG, log.level)


if __name__ == '__main__':
    unittest.main(buffer=False)
