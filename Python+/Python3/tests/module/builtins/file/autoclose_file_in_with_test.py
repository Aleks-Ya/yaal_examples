# Does "with" close a stream? yes.
import os


def test_autoclose_file_in_with():
    path: str = os.path.join(os.path.dirname(__file__), 'data.txt')
    with open(path) as f:
        read_data: str = f.read()
        assert not f.closed
    assert f.closed
    assert read_data == '123'
