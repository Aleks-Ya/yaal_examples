import tempfile


def test_write_file():
    tmp: str = tempfile.mkstemp()[1]

    with open(tmp, 'w') as f:
        exp_content: str = 'abc'
        f.write(exp_content)

    with open(tmp) as f:
        act_content: str = f.readline()

    assert act_content == exp_content
