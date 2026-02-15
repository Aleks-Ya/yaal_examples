import pyperclip


def test_copy_and_paste_string():
    test_string: str = "Hello, clipboard!"
    pyperclip.copy(test_string)
    pasted_string: str = pyperclip.paste()
    assert pasted_string == test_string
