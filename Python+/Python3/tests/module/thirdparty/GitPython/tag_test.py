from datetime import datetime

from git import Repo, TagReference


def test_get_tag(yaal_examples_repo: Repo):
    tag: TagReference = yaal_examples_repo.tag("tmp_tag_1")
    assert "Tmp_Tag_1" == tag.name.title()


def test_is_tag_exists(yaal_examples_repo: Repo):
    tag: TagReference = yaal_examples_repo.tag("absent_tag")
    exists = tag in yaal_examples_repo.tags
    assert not exists


def test_get_tag_commit_time(yaal_examples_repo: Repo):
    tag: TagReference = yaal_examples_repo.tag("tmp_tag_1")
    tag_time: datetime = tag.commit.committed_datetime
    tag_time_epoch_sec: int = int(tag_time.timestamp())
    assert "2024-06-16 09:09:53+07:00" == str(tag_time)
    assert 1718503793 == tag_time_epoch_sec
