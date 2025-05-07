import unittest


class TestIgnoreFunction(unittest.TestCase):

    @unittest.skip("ignore function")
    def test_ignored(self):
        self.fail("Should be ignored")


@unittest.skip("ignore class")
class TestIgnoreClass(unittest.TestCase):

    def test_ignored(self):
        self.fail("Should be ignored")


if __name__ == '__main__':
    unittest.main()
