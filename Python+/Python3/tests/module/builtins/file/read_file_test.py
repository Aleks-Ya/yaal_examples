import os.path


def test_read_file():
    path: str = os.path.join(os.path.dirname(__file__), 'data.txt')
    with open(path) as f:
        read_data: str = f.read()
    assert read_data == '123'
