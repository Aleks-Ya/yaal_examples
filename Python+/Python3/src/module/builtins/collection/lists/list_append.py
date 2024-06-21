import unittest
from typing import List


class TestListAppend(unittest.TestCase):
    def test_list_concatenation(self):
        c: List[int] = [1, 2] + [3, 4]
        self.assertEqual(c, [1, 2, 3, 4])

    def test_list_append(self):
        c: List[int] = [1, 2]
        old_id: id = id(c)
        c += [3, 4]
        new_id: id = id(c)
        self.assertEqual(c, [1, 2, 3, 4])
        self.assertEqual(new_id, old_id)  # old list was appended instead of creating new instance

    def test_element_append(self):
        app: List[int] = [1, 2]
        app.append(3)
        self.assertEqual(app, [1, 2, 3])


if __name__ == "__main__":
    unittest.main()
