from configparser import ConfigParser, SectionProxy
from pathlib import Path


def test_read_ini_file():
    path: Path = Path(__file__).parent / 'config.ini'
    assert path.exists()

    config: ConfigParser = ConfigParser()
    config.read(path)
    assert len(config.sections()) == 0
    assert len(config.items()) == 1

    default_section: SectionProxy = config['DEFAULT']

    name: str = default_section['person.name']
    age: int = default_section.getint('person.age')
    department: str = default_section['department']
    opened: bool = default_section.getboolean('opened')

    assert name == 'John'
    assert age == 30
    assert department == 'Sales'
    assert opened


def test_read_absent_file():
    config: ConfigParser = ConfigParser()
    config.read('absent.ini')
    assert len(config.sections()) == 0
    assert len(config.items()) == 1
