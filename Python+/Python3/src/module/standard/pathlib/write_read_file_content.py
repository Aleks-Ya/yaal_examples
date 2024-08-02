import unittest
import tempfile
from pathlib import Path


class TestWriteReadFileContent(unittest.TestCase):

    def test_temp_file_content(self):
        tmp: str = tempfile.mkstemp()[1]
        exp_content: str = 'abc'

        Path(tmp).write_text(exp_content)

        act_content: str = Path(tmp).read_text()

        self.assertEqual(act_content, exp_content)


if __name__ == '__main__':
    unittest.main()
