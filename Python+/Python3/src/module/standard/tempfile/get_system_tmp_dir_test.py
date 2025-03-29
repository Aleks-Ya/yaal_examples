import tempfile


def test_get_system_tmp_dir():
    tmp_dir: str = tempfile.gettempdir()
    assert isinstance(tmp_dir, str)
    assert tmp_dir != ""
