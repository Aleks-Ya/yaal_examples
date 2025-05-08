def test_join_elements_of_list_to_string():
    string_list: list[str] = ['abcd', 'efg']
    joined: str = ",".join(string_list)
    assert joined == 'abcd,efg'

    num_list: str = '123'
    joined = "','".join(num_list)
    assert joined == "1','2','3"


def test_join_with_transformation1():
    list3: list[str] = ['abcd', 'efg']
    joined3: str = ",".join((element.upper() for element in list3))
    assert joined3 == 'ABCD,EFG'


def test_join_with_transformation2():
    list4: list[str] = ['ab', 'efg']
    joined4: str = ",".join([element.upper() for element in list4])
    assert joined4 == 'AB,EFG'
