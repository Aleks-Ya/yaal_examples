from configparser import ConfigParser, SectionProxy
from pathlib import Path


def test_read_properties_file():
    path: Path = Path(__file__).parent / 'settings.properties'
    assert path.exists()

    config: ConfigParser = ConfigParser()
    config.read_string("[DEFAULT]\n" + path.read_text())
    assert len(config.sections()) == 0
    assert len(config.items()) == 1

    default_section: SectionProxy = config['DEFAULT']

    title: str = default_section['title']
    position: int = default_section.getint('position')
    available: bool = default_section.getboolean('available')

    assert title == 'The Book'
    assert position == 123
    assert available
