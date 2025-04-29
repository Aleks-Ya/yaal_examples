def test_read_file():
    with open('data.txt') as f:
        read_data: str = f.read()
    assert read_data == '123'
