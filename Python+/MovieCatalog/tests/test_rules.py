from phone_sync.rules import has_forbidden_chars, is_default_skipped, matches, parse_marker


def test_empty_marker_means_whole_title():
    assert parse_marker("") == []
    assert parse_marker("\n  \n# just a note\n") == []


def test_marker_patterns_ignore_blanks_and_comments():
    assert parse_marker("*.mkv\n\n# comment\n  Season 0[3-6]/*  \n") == ["*.mkv", "Season 0[3-6]/*"]


def test_pattern_matches_path_or_file_name():
    assert matches("Season 03/x S03E01.avi", ["Season 0[3-6]/*"])
    assert not matches("Season 01/x S01E01.avi", ["Season 0[3-6]/*"])
    assert matches("release/film.mkv", ["*.mkv"])
    assert matches("film.rus.srt", ["film.rus.srt"])
    assert not matches("film.srt", ["*.mkv"])


def test_patterns_are_case_sensitive():
    assert not matches("Film.MKV", ["*.mkv"])


def test_default_skips():
    assert is_default_skipped("[rutor.is]Film.torrent")
    assert is_default_skipped("release/Film.NFO")
    assert not is_default_skipped("Film.mkv")
    assert not is_default_skipped("Film clean.txt")


def test_forbidden_chars():
    assert has_forbidden_chars("English/Alien: Romulus 2024")
    assert has_forbidden_chars('What? 2000')
    assert not has_forbidden_chars("English/Alien - Romulus · Чужой - Ромул 2024 [en ru 2160p]")
