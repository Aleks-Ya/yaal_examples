# Does "with" close a stream? yes.
with open('data.txt') as f:
    read_data = f.read()
    assert not f.closed
assert f.closed
assert read_data == '123'

