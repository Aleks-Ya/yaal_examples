from datetime import datetime

import pytest
from git import Repo, TagReference, Commit


@pytest.fixture
def repo():
    return Repo(".", search_parent_directories=True)


def test_get_repo(repo):
    assert not repo.bare


def test_get_tag(repo):
    tag: TagReference = repo.tag("tmp_tag_1")
    assert "Tmp_Tag_1" == tag.name.title()


def test_is_tag_exists(repo):
    tag: TagReference = repo.tag("absent_tag")
    exists = tag in repo.tags
    assert not exists


def test_get_tag_commit_time(repo):
    tag: TagReference = repo.tag("tmp_tag_1")
    tag_time: datetime = tag.commit.committed_datetime
    tag_time_epoch_sec: int = int(tag_time.timestamp())
    assert "2024-06-16 09:09:53+07:00" == str(tag_time)
    assert 1718503793 == tag_time_epoch_sec


def test_get_latest_commit_in_current_branch(repo):
    head: Commit = repo.head.commit
    print(f"Latest commit: {head}")
