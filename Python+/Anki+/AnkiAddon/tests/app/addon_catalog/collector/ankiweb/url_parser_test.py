from app.addon_catalog.collector.ankiweb.url_parser import UrlParser
from app.addon_catalog.common.data_types import URL, GitHubLink, GitHubRepo, GitHubUser, GithubUserName, GithubRepoName


def test_find_github_links():
    links: list[URL] = [
        URL("https://github.com/Aleks-Ya/note-size-anki-addon/issues"),
        URL("https://github.com/shigeyukey/AnkiRestart"),
        URL("https://ankiweb.net/logo.png"),
        URL("https://github.com/Arthur-Milchior/anki-copy-note?tab=readme-ov-file#copy-notes"),
        URL("https://github.com/kanjieater/anki-plugin-heisigs-rtk#readme"),
        URL("https://github.com/Arthur-Milchior/note-organizer)*")
    ]
    github_links: list[GitHubLink] = UrlParser.find_github_links(links)
    assert github_links == [
        GitHubLink(URL("https://github.com/Aleks-Ya/note-size-anki-addon/issues"),
                   GitHubUser(GithubUserName("aleks-ya")),
                   GitHubRepo(GithubUserName("aleks-ya"), GithubRepoName("note-size-anki-addon"))),
        GitHubLink(URL("https://github.com/shigeyukey/AnkiRestart"),
                   GitHubUser(GithubUserName("shigeyukey")),
                   GitHubRepo(GithubUserName("shigeyukey"), GithubRepoName("ankirestart"))),
        GitHubLink(URL("https://github.com/Arthur-Milchior/anki-copy-note?tab=readme-ov-file#copy-notes"),
                   GitHubUser(GithubUserName("arthur-milchior")),
                   GitHubRepo(GithubUserName("arthur-milchior"), GithubRepoName("anki-copy-note"))),
        GitHubLink(URL("https://github.com/kanjieater/anki-plugin-heisigs-rtk#readme"),
                   GitHubUser(GithubUserName("kanjieater")),
                   GitHubRepo(GithubUserName("kanjieater"), GithubRepoName("anki-plugin-heisigs-rtk"))),
        GitHubLink(URL("https://github.com/Arthur-Milchior/note-organizer)*"),
                   GitHubUser(GithubUserName("arthur-milchior")),
                   GitHubRepo(GithubUserName("arthur-milchior"), GithubRepoName("note-organizer")))
    ]


def test_find_anki_forum_links():
    links: list[URL] = [
        URL("https://github.com/Aleks-Ya/note-size-anki-addon/issues"),
        URL("https://ankiweb.net/logo.png"),
        URL("https://forums.ankiweb.net/t/batch-create-filtered-decks-official-thread/545")
    ]
    anki_forum_links: list[URL] = UrlParser.find_anki_forum_links(links)
    assert anki_forum_links == [
        URL("https://forums.ankiweb.net/t/batch-create-filtered-decks-official-thread/545")
    ]
