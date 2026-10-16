from pathlib import Path

import pytest
from configparser import ConfigParser
from pytapo import Tapo


@pytest.fixture
def tapo() -> Tapo:
    config_file: Path = Path.home() / ".config" / "tplink-tapo-c260-camera" / "camera.ini"
    config: ConfigParser = ConfigParser()
    with config_file.open(encoding="utf-8") as config_stream:
        config.read_file(config_stream)
    user: str = config["camera"]["user"]
    password: str = config["camera"]["password"]
    host: str = config["camera"]["host"]
    cloud_password: str = config["camera"]["cloud_password"]
    return Tapo(host=host, user=user, password=password, cloudPassword=cloud_password)
