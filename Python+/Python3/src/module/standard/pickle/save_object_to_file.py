import unittest
import pickle
import tempfile


class TestPickle(unittest.TestCase):
    def test_pickle_dump_load(self):
        filename: str = tempfile.mktemp()
        exp_data: list[int] = [1, 3, 5]
        pickle.dump(exp_data, open(filename, 'wb'))
        act_data: list[int] = pickle.load(open(filename, 'rb'))
        self.assertEqual(exp_data, act_data)


if __name__ == '__main__':
    unittest.main()
