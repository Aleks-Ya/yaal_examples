from pandas import DataFrame


def test_create_data_frame_from_one_dict():
    df: DataFrame = DataFrame({'Name': ['John', 'Mary'], 'Age': [30, 25]})
    assert df.to_string() == """   Name  Age\n0  John   30\n1  Mary   25"""


def test_create_data_frame_from_dict():
    df: DataFrame = DataFrame.from_dict({'Name': ['John', 'Mary'], 'Age': [30, 25]})
    assert df.to_string() == """   Name  Age\n0  John   30\n1  Mary   25"""


def test_create_data_frame_from_list_of_dicts():
    df: DataFrame = DataFrame([{'country': 'USA'}, {'country': 'Germany'}])
    assert df.to_string() == """   country\n0      USA\n1  Germany"""
