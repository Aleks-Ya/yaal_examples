def test_format():
    greeting: str = 'Hi'
    name: str = 'John'
    formatted: str = "{} {}!".format(greeting, name)
    assert formatted == 'Hi John!'
