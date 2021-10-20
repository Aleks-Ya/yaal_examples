import tempfile
from pathlib import Path

tmp = tempfile.mkstemp()[1]
exp_content = 'abc'

Path(tmp).write_text(exp_content)

act_content = Path(tmp).read_text()

assert act_content == exp_content
