from pathlib import Path


def test_write_file_en(temp_path_absent: Path):
    exp_content: str = 'abc'
    with open(temp_path_absent, 'w') as f:
        f.write(exp_content)
    with open(temp_path_absent) as f:
        act_content: str = f.readline()
    assert act_content == exp_content


def test_write_file_ru(temp_path_absent: Path):
    exp_content: str = 'Русский'
    with open(temp_path_absent, 'w', encoding='utf8') as f:
        f.write(exp_content)
    with open(temp_path_absent, encoding='utf8') as f:
        act_content: str = f.readline()
    print(act_content)
    assert act_content == exp_content


def test_append_file(temp_path_absent: Path):
    existing_content: str = 'Existing text '
    appended_content: str = 'Appended text'
    temp_path_absent.write_text(existing_content)
    with open(temp_path_absent, 'a') as f:
        f.write(appended_content)
    with open(temp_path_absent) as f:
        act_content: str = f.readline()
    assert act_content == f"{existing_content}{appended_content}"


def test_overwrite_file(temp_path_absent: Path):
    existing_content: str = 'Existing text '
    new_content: str = 'Appended text'
    temp_path_absent.write_text(existing_content)
    with open(temp_path_absent, 'w') as f:
        f.write(new_content)
    with open(temp_path_absent) as f:
        act_content: str = f.readline()
    assert act_content == new_content
