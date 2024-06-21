import logging
import unittest
from logging import INFO, DEBUG, Logger


class TestParseLevel(unittest.TestCase):

    def test_pares_logging_level(self):
        level_str: str = "DEBUG"
        level_int: int = INFO
        log: Logger = logging.getLogger()
        log.setLevel(level_int)
        self.assertEqual(INFO, log.level)
        log.setLevel(level_str)
        self.assertEqual(DEBUG, log.level)

    def test_get_level_name(self):
        int_to_str: str = logging.getLevelName(DEBUG)
        str_to_int: int = logging.getLevelName("INFO")
        self.assertEqual("DEBUG", int_to_str)
        self.assertEqual(20, str_to_int)


if __name__ == '__main__':
    unittest.main(buffer=False)
