def test_generator_to_dict():
    names: list[str] = ['John', 'Mary']
    names_dict: dict[str, str] = {name: name.upper() for name in names}
    assert names_dict == {'John': 'JOHN', 'Mary': 'MARY'}
