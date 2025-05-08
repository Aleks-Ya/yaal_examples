import sys
from pathlib import Path


def test_path_append():
    sys_path: list[str] = sys.path
    src_dir: str = str(Path(__file__).parent / "my_modules")
    sys_path.append(src_dir)

    from hello_import import get_seven
    assert get_seven() == 7

    from strings_naming_package.my_strings import get_hello
    assert get_hello() == "Hello"
