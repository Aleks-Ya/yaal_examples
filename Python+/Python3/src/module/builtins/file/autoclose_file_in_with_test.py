# Does "with" close a stream? yes.
def test_autoclose_file_in_with():
    with open('data.txt') as f:
        read_data = f.read()
        assert not f.closed
    assert f.closed
    assert read_data == '123'
