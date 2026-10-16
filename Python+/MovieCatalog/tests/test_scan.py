from conftest import make_tree

from phone_sync.scan import scan_library


def test_scans_titles_recursively(tmp_path):
    make_tree(tmp_path, {
        "English/Film 2000 [en 480p]/film.mkv": 10,
        "English/Film 2000 [en 480p]/film.srt": 3,
        "English/Show 1999 [ru 480p series]/Season 01/Show S01E01.avi": 5,
        "Russian/Фильм 2008 [ru 480p]/фильм.mkv": 7,
    })
    titles = scan_library(tmp_path)
    assert sorted(titles) == [
        "English/Film 2000 [en 480p]",
        "English/Show 1999 [ru 480p series]",
        "Russian/Фильм 2008 [ru 480p]",
    ]
    assert titles["English/Film 2000 [en 480p]"].files == {"film.mkv": 10, "film.srt": 3}
    assert titles["English/Show 1999 [ru 480p series]"].files == {"Season 01/Show S01E01.avi": 5}


def test_ignores_tagspaces_hidden_folders_and_loose_files(tmp_path):
    make_tree(tmp_path, {
        "English/Film/film.mkv": 10,
        "English/Film/.ts/tsm.json": 2,
        "English/.ts/tsm.json": 2,
        "English/loose.txt": 1,
        "Other/Film/film.mkv": 10,
    })
    titles = scan_library(tmp_path)
    assert list(titles) == ["English/Film"]
    assert titles["English/Film"].files == {"film.mkv": 10}


def test_reads_marker_but_does_not_list_it(tmp_path):
    make_tree(tmp_path, {
        "English/Film/film.mkv": 10,
        "English/Film/.no-phone": "*.mkv\n",
        "English/Other/other.mkv": 10,
    })
    titles = scan_library(tmp_path)
    assert titles["English/Film"].marker == "*.mkv\n"
    assert titles["English/Film"].files == {"film.mkv": 10}
    assert titles["English/Other"].marker is None


def test_missing_root_or_categories(tmp_path):
    assert scan_library(tmp_path / "nope") == {}
    assert scan_library(tmp_path) == {}
