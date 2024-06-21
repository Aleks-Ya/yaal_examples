import unittest


class TestJoin(unittest.TestCase):

    def test_join_elements_of_list_to_string(self):
        string_list: list[str] = ['abcd', 'efg']
        joined: str = ",".join(string_list)
        self.assertEqual(joined, 'abcd,efg')

        num_list: str = '123'
        joined: str = "','".join(num_list)
        self.assertEqual(joined, "1','2','3")

    def test_join_with_transformation1(self):
        list3: list[str] = ['abcd', 'efg']
        joined3: str = ",".join((element.upper() for element in list3))
        self.assertEqual(joined3, 'ABCD,EFG')

    def test_join_with_transformation2(self):
        list4: list[str] = ['ab', 'efg']
        joined4: str = ",".join([element.upper() for element in list4])
        self.assertEqual(joined4, 'AB,EFG')


if __name__ == "__main__":
    unittest.main()
