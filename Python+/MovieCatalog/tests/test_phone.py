import pytest
from conftest import FakeGio

from phone_sync.phone import (
    NO_TRANSFER,
    NOT_FOUND,
    READ_ONLY,
    Gio,
    GioError,
    PhoneError,
    check_writable,
    find_movies_dir,
)

HOST = "mtp:host=SAMSUNG_SAMSUNG_Android_TEST"


def test_no_phone(tmp_path):
    with pytest.raises(PhoneError) as e:
        find_movies_dir(tmp_path)
    assert str(e.value) == NOT_FOUND


def test_no_gvfs_root_at_all(tmp_path):
    with pytest.raises(PhoneError, match="Phone not found"):
        find_movies_dir(tmp_path / "missing")


def test_connected_without_file_transfer(tmp_path):
    (tmp_path / HOST).mkdir()
    with pytest.raises(PhoneError) as e:
        find_movies_dir(tmp_path)
    assert str(e.value) == NO_TRANSFER


def test_locked_phone_shows_empty_storage(tmp_path):
    (tmp_path / HOST / "Internal storage").mkdir(parents=True)
    with pytest.raises(PhoneError) as e:
        find_movies_dir(tmp_path)
    assert str(e.value) == NO_TRANSFER


def test_several_phones(tmp_path):
    (tmp_path / "mtp:host=A").mkdir()
    (tmp_path / "mtp:host=B").mkdir()
    with pytest.raises(PhoneError, match="--phone"):
        find_movies_dir(tmp_path)


def test_finds_movies_dir(tmp_path):
    storage = tmp_path / HOST / "Internal storage"
    (storage / "DCIM").mkdir(parents=True)
    assert find_movies_dir(tmp_path) == storage / "Movies"


def test_writable_probe_is_created_and_removed(tmp_path, fake_gio):
    check_writable(Gio(fake_gio), tmp_path)
    assert [c[1] for c in fake_gio.calls] == ["info", "save", "remove"]
    assert list(tmp_path.iterdir()) == []


def test_probe_uses_parent_when_movies_missing(tmp_path, fake_gio):
    check_writable(Gio(fake_gio), tmp_path / "Movies")
    assert fake_gio.commands("save")[0][2] == str(tmp_path / ".sync-phone-probe")


def test_read_only_storage(tmp_path):
    with pytest.raises(PhoneError) as e:
        check_writable(Gio(FakeGio(readonly=True)), tmp_path)
    assert str(e.value) == READ_ONLY


def test_failed_probe_reports_gio_error(tmp_path, fake_gio):
    fake_gio.fail["save"] = "Operation not supported"
    with pytest.raises(PhoneError, match="not writable: Operation not supported"):
        check_writable(Gio(fake_gio), tmp_path)


def test_gio_parses_free_space(tmp_path):
    assert Gio(FakeGio(free=12345)).free_space(tmp_path) == 12345


def test_gio_error_without_stderr(tmp_path):
    def run(cmd, **kwargs):
        import subprocess
        return subprocess.CompletedProcess(cmd, 3, None, None)

    with pytest.raises(GioError, match="gio copy failed with exit code 3"):
        Gio(run).copy(tmp_path / "a", tmp_path / "b")


def test_move_never_falls_back_to_copy(tmp_path, fake_gio):
    (tmp_path / "a").mkdir()
    (tmp_path / "dst").mkdir()
    Gio(fake_gio).move_into(tmp_path / "a", tmp_path / "dst")
    assert "--no-copy-fallback" in fake_gio.commands("move")[0]
    assert (tmp_path / "dst" / "a").is_dir()
