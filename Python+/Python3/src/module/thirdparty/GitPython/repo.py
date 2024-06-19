import unittest
from datetime import datetime

from git import Repo, TagReference, Commit


class TestRepo(unittest.TestCase):

    def setUp(self):
        self.repo: Repo = Repo(".", search_parent_directories=True)

    def test_get_repo(self):
        self.assertFalse(self.repo.bare)

    def test_get_tag(self):
        tag: TagReference = self.repo.tag("tmp_tag_1")
        self.assertEqual("Tmp_Tag_1", tag.name.title())

    def test_is_tag_exists(self):
        tag: TagReference = self.repo.tag("absent_tag")
        exists = tag in self.repo.tags
        self.assertFalse(exists)

    def test_get_tag_commit_time(self):
        tag: TagReference = self.repo.tag("tmp_tag_1")
        tag_time: datetime = tag.commit.committed_datetime
        tag_time_epoch_sec: int = int(tag_time.timestamp())
        self.assertEqual("2024-06-16 09:09:53+07:00", str(tag_time))
        self.assertEqual(1718503793, tag_time_epoch_sec)

    def test_get_latest_commit_in_current_branch(self):
        head: Commit = self.repo.head.commit
        print(f"Latest commit: {head}")


if __name__ == '__main__':
    unittest.main()
