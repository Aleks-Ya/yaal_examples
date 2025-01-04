from pathlib import Path


def test_user_home_dir():
    user_home_dir: Path = Path.home()
    assert user_home_dir is not None
