from pathlib import Path

from app.addon_catalog.collector.overrider.overrider import Overrider
from app.addon_catalog.common.data_types import URL, GitHubLink, AddonId, GitHubUser, GithubUserName, GitHubRepo, \
    GithubRepoName


def test_override_github_link(overrider: Overrider, note_size_addon_id: AddonId):
    assert overrider.override_github_link(note_size_addon_id) is None
    override_link: GitHubLink = overrider.override_github_link(AddonId(1984823157))
    github_user_name: GithubUserName = GithubUserName("r-appleton")
    assert override_link == GitHubLink(URL("https://github.com/r-appleton/addons"),
                                       GitHubUser(github_user_name),
                                       GitHubRepo(github_user_name, GithubRepoName("addons")))


def test_override_anki_forum_url(overrider: Overrider, note_size_addon_id: AddonId, hyper_tts_addon_id: AddonId):
    assert overrider.override_anki_forum_url(note_size_addon_id) is None
    assert overrider.override_anki_forum_url(hyper_tts_addon_id) == URL(
        "https://forums.ankiweb.net/t/hypertts-spirtual-successor-to-awesometts/17143")


def test_copy_override_yaml_to_dataset(overrider: Overrider, dataset_path: Path):
    assert (dataset_path / "raw" / "4-overrider" / "overrides.yaml").exists()
