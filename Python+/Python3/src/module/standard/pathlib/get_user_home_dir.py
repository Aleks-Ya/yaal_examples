import unittest
from pathlib import Path


class TestGetUserHomeDir(unittest.TestCase):

    def test_user_home_dir(self):
        user_home_dir: Path = Path.home()
        self.assertIsNotNone(user_home_dir)


if __name__ == '__main__':
    unittest.main()
