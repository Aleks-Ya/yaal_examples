from configparser import ConfigParser, SectionProxy
from pathlib import Path

import pytest
from joppy.client_api import ClientApi


@pytest.fixture
def api() -> ClientApi:
    config: ConfigParser = ConfigParser()
    config.read(Path.home() / ".joppy" / "joppy.ini")
    default_section: SectionProxy = config['DEFAULT']
    token: str = default_section['joplin.web.clipper.token']
    return ClientApi(token=token)
