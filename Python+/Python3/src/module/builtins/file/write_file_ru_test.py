import tempfile


def test_write_file():
    tmp: str = tempfile.mkstemp()[1]

    exp_content: str = 'Русский'

    with open(tmp, 'w', encoding='utf8') as f:
        f.write(exp_content)

    with open(tmp, encoding='utf8') as f:
        act_content: str = f.readline()

    print(act_content)
    assert act_content == exp_content
