# NOT WORK!!! Reddit API is deprecated

from configparser import ConfigParser
from pathlib import Path
from typing import Any, Iterator

import praw
import pytest
from praw import Reddit
from praw.models import Subreddit


@pytest.fixture
def config() -> ConfigParser:
    file: Path = Path.home() / ".reddit" / "reddit.toml"
    config: ConfigParser = ConfigParser()
    config.read(file)
    return config


# https://praw.readthedocs.io/en/stable/getting_started/quick_start.html#read-only-reddit-instances
def test_read_only(config: ConfigParser):
    client_id: str = config['default']['client_id']
    client_secret: str = config['default']['client_secret']

    reddit: Reddit = praw.Reddit(
        client_id=client_id,
        client_secret=client_secret,
        user_agent="my user agent",
    )
    assert reddit.read_only

    subreddit: Subreddit = reddit.subreddit("test")
    hot10: Iterator[Any] = subreddit.hot(limit=10)
    for submission in hot10:
        print(submission.title)


# https://praw.readthedocs.io/en/stable/getting_started/authentication.html#password-flow
def test_password_flow(config: ConfigParser):
    client_id: str = config['default']['client_id']
    client_secret: str = config['default']['client_secret']
    username: str = config['default']['username']
    password: str = config['default']['password']

    reddit: Reddit = praw.Reddit(
        client_id=client_id,
        client_secret=client_secret,
        user_agent="my user agent",
        username=username,
        password=password,
    )
    assert not reddit.read_only

    print(reddit.user.me())
