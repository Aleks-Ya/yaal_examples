import tempfile
from configparser import ConfigParser
from pathlib import Path


def test_write_ini_file():
    _, output_file = tempfile.mkstemp(suffix='.ini')
    print(output_file)
    path: Path = Path(output_file)
    assert path.read_text() == ""

    config: ConfigParser = ConfigParser()
    config['Section1'] = {'option1': 'value1',
                          'option2': 'value2'}
    with open(path, 'w') as f:
        config.write(f)

    assert path.read_text() == "[Section1]\noption1 = value1\noption2 = value2\n\n"
