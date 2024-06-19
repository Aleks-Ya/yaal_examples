import textwrap

import unittest


class TestShorten(unittest.TestCase):

    def test_shorten(self):
        s: str = "A long long long long long text"
        act_str: str = textwrap.shorten(s, width=10, placeholder="...")
        exp_str: str = "A long..."
        self.assertEqual(exp_str, act_str)


if __name__ == '__main__':
    unittest.main()
