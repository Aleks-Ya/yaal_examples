import tempfile
from pathlib import Path


def test_temp_file_content():
    tmp: str = tempfile.mkstemp()[1]
    exp_content: str = 'abc'
    Path(tmp).write_text(exp_content)
    act_content: str = Path(tmp).read_text()
    assert act_content == exp_content
