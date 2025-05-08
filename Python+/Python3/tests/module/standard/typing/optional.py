import unittest
from typing import Optional


class TestOptional(unittest.TestCase):

    def test_notNoneOpt(self):
        not_none_opt: Optional[int] = 2
        self.assertIsNotNone(not_none_opt)
        self.assertEqual(not_none_opt * 3, 6)

    def test_noneOpt(self):
        none_opt: Optional[int] = None
        self.assertIsNone(none_opt)


if __name__ == '__main__':
    unittest.main()
