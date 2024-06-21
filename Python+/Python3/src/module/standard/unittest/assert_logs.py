import logging
import unittest
from logging import Logger


class TestAssertLogs(unittest.TestCase):

    def test_assert_log(self):
        log: Logger = logging.getLogger()
        with self.assertLogs(log, logging.DEBUG) as cm:
            logging.getLogger('foo').info('first message')
            logging.getLogger('foo. bar').error('second message')
            self.assertEqual(cm.output, ['INFO:foo:first message',
                                         'ERROR:foo. bar:second message'])


if __name__ == '__main__':
    unittest.main()
