from pathlib import Path

import pytest
from conftest import FakeGio, make_tree

from phone_sync.cli import main


@pytest.fixture
def drive(tmp_path) -> Path:
    return make_tree(tmp_path / "drive", {
        "English/Film · Фильм 2000 [en ru 480p]/release.mkv": 100,
        "English/Film · Фильм 2000 [en ru 480p]/release.srt": 5,
        "English/Film · Фильм 2000 [en ru 480p]/release.torrent": 1,
        "English/Skip 2001/skip.mkv": 50,
        "English/Skip 2001/.no-phone": "",
        "Russian/Фильм 2008 [ru 480p]/фильм.mkv": 30,
    })


@pytest.fixture
def phone(tmp_path) -> Path:
    path = tmp_path / "phone" / "Movies"
    path.mkdir(parents=True)
    return path


def files(root: Path) -> dict[str, int]:
    return {p.relative_to(root).as_posix(): p.stat().st_size
            for p in sorted(root.rglob("*")) if p.is_file()}


def run(drive, phone, *args, gio=None, answers=("y",)):
    gio = gio or FakeGio()
    replies = iter(answers)
    code = main(["--drive", str(drive), "--phone", str(phone), "--reserve", "0", *args],
                run=gio, ask=lambda prompt: next(replies))
    return code, gio


def test_dry_run_changes_nothing(drive, phone, capsys):
    code, gio = run(drive, phone, "--dry-run")
    assert code == 0
    assert files(phone) == {}
    assert not gio.commands("copy")
    out = capsys.readouterr().out
    assert "English/Film · Фильм 2000 [en ru 480p]  (2 files" in out
    assert "Skipped (.no-phone):\n  English/Skip 2001" in out
    assert "Create on phone:\n  English/\n  Russian/" in out
    assert "Dry run" in out


def test_full_sync_then_up_to_date(drive, phone, capsys):
    code, _ = run(drive, phone)
    assert code == 0
    assert files(phone) == {
        "English/Film · Фильм 2000 [en ru 480p]/release.mkv": 100,
        "English/Film · Фильм 2000 [en ru 480p]/release.srt": 5,
        "Russian/Фильм 2008 [ru 480p]/фильм.mkv": 30,
    }
    capsys.readouterr()
    code, gio = run(drive, phone, answers=())
    assert code == 0
    assert "Phone is up to date." in capsys.readouterr().out
    assert not gio.commands("copy")


def test_copies_through_partial_name(drive, phone):
    _, gio = run(drive, phone)
    copy = gio.commands("copy")[0]
    assert copy[-1].endswith(".partial")


def test_answer_no_cancels(drive, phone):
    code, gio = run(drive, phone, answers=("n",))
    assert code == 1
    assert files(phone) == {}
    assert not gio.commands("copy")


def test_yes_flag_skips_prompt(drive, phone):
    code, _ = run(drive, phone, "--yes", answers=())
    assert code == 0
    assert len(files(phone)) == 3


def test_not_enough_space(drive, phone, capsys):
    gio = FakeGio(free=100)
    code = main(["--drive", str(drive), "--phone", str(phone), "--reserve", "0"],
                run=gio, ask=lambda p: "y")
    assert code == 1
    assert "Not enough space" in capsys.readouterr().err
    assert files(phone) == {}


def test_renamed_folder_is_followed(drive, phone):
    make_tree(phone, {"English/Film 2000/release.mkv": 100})
    code, gio = run(drive, phone)
    assert code == 0
    assert gio.commands("rename")[0][-1] == "Film · Фильм 2000 [en ru 480p]"
    copied = [c[-2] for c in gio.commands("copy")]
    assert not any(c.endswith("release.mkv") for c in copied)
    assert "English/Film 2000" not in {p.name for p in (phone / "English").iterdir()}


def test_resized_file_is_replaced(drive, phone):
    make_tree(phone, {"Russian/Фильм 2008 [ru 480p]/фильм.mkv": 7})
    run(drive, phone)
    assert files(phone)["Russian/Фильм 2008 [ru 480p]/фильм.mkv"] == 30


def test_prune_deletes_after_confirmation(drive, phone):
    make_tree(phone, {
        "English/Skip 2001/skip.mkv": 50,
        "English/Gone 1990/gone.avi": 3,
    })
    code, _ = run(drive, phone, "--prune", answers=("y", "y"))
    assert code == 0
    assert not (phone / "English/Skip 2001").exists()
    assert not (phone / "English/Gone 1990").exists()


def test_extras_kept_without_prune(drive, phone, capsys):
    make_tree(phone, {"English/Gone 1990/gone.avi": 3})
    run(drive, phone)
    assert (phone / "English/Gone 1990/gone.avi").exists()
    assert "use --prune" in capsys.readouterr().out


def test_failed_copy_continues_and_reports(drive, phone, capsys):
    gio = FakeGio()
    gio.fail["copy"] = "Connection lost"
    code, _ = run(drive, phone, gio=gio)
    assert code == 1
    assert len(gio.commands("copy")) == 3
    assert "Connection lost" in capsys.readouterr().err


def test_phone_not_connected(drive, tmp_path, capsys):
    code = main(["--drive", str(drive)], run=FakeGio(), gvfs_root=tmp_path / "gvfs")
    assert code == 2
    assert "Phone not found" in capsys.readouterr().err


def test_phone_not_writable(drive, phone, capsys):
    gio = FakeGio()
    gio.fail["save"] = "Permission denied"
    code, _ = run(drive, phone, gio=gio)
    assert code == 2
    assert "not writable: Permission denied" in capsys.readouterr().err


def test_bad_name_is_shown_and_not_copied(drive, phone, capsys):
    make_tree(drive, {"English/Alien: Romulus 2024/a.mkv": 10})
    code, gio = run(drive, phone)
    assert code == 0
    assert "Android rejects" in capsys.readouterr().out
    assert not (phone / "English/Alien: Romulus 2024").exists()


FILM = "English/Film · Фильм 2000 [en ru 480p]"


def test_dry_run_shows_file_moves(drive, phone, capsys):
    make_tree(phone, {f"{FILM}/Rel/release.mkv": 100})
    run(drive, phone, "--dry-run")
    assert f"Move within title on phone:\n  {FILM}: Rel/release.mkv  ->  release.mkv" in capsys.readouterr().out


def test_flattened_folder_is_followed(drive, phone):
    make_tree(phone, {f"{FILM}/Rel/release.mkv": 100, f"{FILM}/Rel/release.srt": 5})
    code, gio = run(drive, phone)
    assert code == 0
    assert all("--no-copy-fallback" in c for c in gio.commands("move"))
    copied = [c[-2] for c in gio.commands("copy")]
    assert not any(c.startswith(str(drive / FILM)) for c in copied)
    assert files(phone / FILM) == {"release.mkv": 100, "release.srt": 5}
    assert not (phone / FILM / "Rel").exists()


def test_failed_file_move_is_reported_not_copied(drive, phone, capsys):
    make_tree(phone, {f"{FILM}/Rel/release.mkv": 100})
    gio = FakeGio()
    gio.fail["move"] = "Device busy"
    code, _ = run(drive, phone, gio=gio)
    assert code == 1
    assert "Device busy" in capsys.readouterr().err
    copied = [c[-2] for c in gio.commands("copy")]
    assert not any(c.endswith("release.mkv") for c in copied)
    assert (phone / FILM / "Rel/release.mkv").exists()
