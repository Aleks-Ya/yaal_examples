from phone_sync.planner import Copy, FileMove, Move, build_plan
from phone_sync.scan import Title


def lib(**titles) -> dict[str, Title]:
    """lib(**{"English/A": {"a.mkv": 10}}) -> {key: Title}; a "marker" key sets the marker."""
    result = {}
    for key, files in titles.items():
        files = dict(files)
        marker = files.pop("marker", None)
        result[key] = Title(key, files, marker)
    return result


def test_new_title_is_copied_without_default_skips():
    plan = build_plan(lib(**{"English/A": {"a.mkv": 10, "a.srt": 2, "a.torrent": 1}}), {})
    assert plan.copies == [Copy("English/A", "a.mkv", 10), Copy("English/A", "a.srt", 2)]
    assert plan.bytes_to_copy == 12
    assert plan.has_work


def test_up_to_date_title_needs_nothing():
    files = {"English/A": {"a.mkv": 10}}
    plan = build_plan(lib(**files), lib(**files))
    assert not plan.has_work
    assert plan.extras == []


def test_missing_and_resized_files_are_copied():
    drive = lib(**{"English/A": {"a.mkv": 10, "a.srt": 2}})
    phone = lib(**{"English/A": {"a.mkv": 4}})  # interrupted copy
    plan = build_plan(drive, phone)
    assert plan.copies == [Copy("English/A", "a.mkv", 10, old_size=4), Copy("English/A", "a.srt", 2)]
    assert plan.free_after(100) == 100 - 12 + 4


def test_whole_title_marker_skips_and_reports_phone_copy():
    drive = lib(**{"English/A": {"a.mkv": 10, "marker": ""}})
    phone = lib(**{"English/A": {"a.mkv": 10}})
    plan = build_plan(drive, phone)
    assert plan.skipped_titles == ["English/A"]
    assert plan.copies == []
    assert plan.extras == ["English/A"]


def test_marker_patterns_skip_matching_files():
    drive = lib(**{"English/S": {
        "Season 01/S S01E01.avi": 5,
        "Season 03/S S03E01.avi": 6,
        "marker": "Season 03/*\n",
    }})
    plan = build_plan(drive, {})
    assert plan.copies == [Copy("English/S", "Season 01/S S01E01.avi", 5)]
    assert plan.skipped_files == [("English/S", "Season 03/S S03E01.avi", 6)]


def test_renamed_folder_is_moved_not_copied():
    drive = lib(**{"English/Film · Фильм 2000 [en ru 480p]": {"release.mkv": 100, "release.srt": 2}})
    phone = lib(**{"English/Film 2000": {"release.mkv": 100, "release.torrent": 1}})
    plan = build_plan(drive, phone)
    assert plan.moves == [Move("English/Film 2000", "English/Film · Фильм 2000 [en ru 480p]")]
    assert plan.copies == [Copy("English/Film · Фильм 2000 [en ru 480p]", "release.srt", 2)]
    assert plan.extras == ["English/Film · Фильм 2000 [en ru 480p]/release.torrent"]


def test_title_moved_between_categories():
    drive = lib(**{"Russian/Фильм": {"f.mkv": 100}})
    phone = lib(**{"English/Фильм": {"f.mkv": 100}})
    plan = build_plan(drive, phone)
    assert plan.moves == [Move("English/Фильм", "Russian/Фильм")]
    assert plan.copies == []


def test_ambiguous_match_is_not_moved():
    drive = lib(**{"English/A": {"same.mkv": 100}, "English/B": {"same.mkv": 100}})
    phone = lib(**{"English/Old": {"same.mkv": 100}})
    plan = build_plan(drive, phone)
    assert plan.moves == []
    assert len(plan.copies) == 2
    assert plan.extras == ["English/Old"]


def test_size_mismatch_is_not_a_rename():
    drive = lib(**{"English/New": {"f.mkv": 100}})
    phone = lib(**{"English/Old": {"f.mkv": 99}})
    plan = build_plan(drive, phone)
    assert plan.moves == []
    assert plan.extras == ["English/Old"]


def test_extras_are_reported_never_copied_over():
    drive = lib(**{"English/A": {"a.mkv": 10}})
    phone = lib(**{"English/A": {"a.mkv": 10, "old.srt": 1}, "English/Gone": {"g.mkv": 5}})
    plan = build_plan(drive, phone)
    assert plan.extras == ["English/A/old.srt", "English/Gone"]
    assert not plan.has_work


def test_copies_are_ordered_smallest_title_first():
    drive = lib(**{"English/Big": {"b.mkv": 100}, "English/Small": {"s2.mkv": 3, "s1.mkv": 2}})
    plan = build_plan(drive, {})
    assert [(c.title, c.rel) for c in plan.copies] == [
        ("English/Small", "s1.mkv"), ("English/Small", "s2.mkv"), ("English/Big", "b.mkv"),
    ]


def test_names_android_rejects_are_reported_not_copied():
    drive = lib(**{
        "English/Alien: Romulus 2024": {"a.mkv": 10},
        "English/Ok 2000": {"ok.mkv": 5, "what?.srt": 1},
    })
    plan = build_plan(drive, {})
    assert plan.bad_names == ["English/Alien: Romulus 2024", "English/Ok 2000/what?.srt"]
    assert plan.copies == [Copy("English/Ok 2000", "ok.mkv", 5)]


def test_flattened_release_folder_is_moved_not_copied():
    drive = lib(**{"English/A": {"r.mkv": 100, "r.srt": 2}})
    phone = lib(**{"English/A": {"Rel/r.mkv": 100, "Rel/r.srt": 2}})
    plan = build_plan(drive, phone)
    assert plan.file_moves == [FileMove("English/A", "Rel/r.mkv", "r.mkv"),
                               FileMove("English/A", "Rel/r.srt", "r.srt")]
    assert plan.copies == []
    assert plan.extras == []
    assert plan.has_work


def test_file_moved_into_season_folder():
    drive = lib(**{"English/S": {"Season 01/e1.avi": 50}})
    phone = lib(**{"English/S": {"e1.avi": 50}})
    plan = build_plan(drive, phone)
    assert plan.file_moves == [FileMove("English/S", "e1.avi", "Season 01/e1.avi")]


def test_same_name_other_size_is_copied_not_moved():
    drive = lib(**{"English/A": {"r.mkv": 100}})
    phone = lib(**{"English/A": {"Rel/r.mkv": 99}})
    plan = build_plan(drive, phone)
    assert plan.file_moves == []
    assert plan.copies == [Copy("English/A", "r.mkv", 100)]
    assert plan.extras == ["English/A/Rel/r.mkv"]


def test_ambiguous_file_move_is_not_made():
    drive = lib(**{"English/A": {"r.srt": 2}})
    phone = lib(**{"English/A": {"X/r.srt": 2, "Y/r.srt": 2}})
    plan = build_plan(drive, phone)
    assert plan.file_moves == []
    assert plan.copies == [Copy("English/A", "r.srt", 2)]


def test_renamed_and_flattened_title():
    drive = lib(**{"English/New": {"r.mkv": 100}})
    phone = lib(**{"English/Old": {"Rel/r.mkv": 100}})
    plan = build_plan(drive, phone)
    assert plan.moves == [Move("English/Old", "English/New")]
    assert plan.file_moves == [FileMove("English/New", "Rel/r.mkv", "r.mkv")]
    assert plan.copies == []
    assert plan.extras == []
