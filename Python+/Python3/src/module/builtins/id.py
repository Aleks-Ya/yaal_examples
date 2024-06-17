import unittest


class IdCase(unittest.TestCase):
    def test_get_object_id(self):
        obj1: str = "abc"
        id_1: int = id(obj1)
        print(id_1)

        obj2: str = "abc"
        id_2: int = id(obj2)
        print(id_2)

        self.assertEqual(id_1, id_2)


if __name__ == '__main__':
    unittest.main()
