def test_format():
    name: str = 'John'
    formatted: str = "Hi %s!" % name
    assert formatted == 'Hi John!'
