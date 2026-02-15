import contextlib
from pathlib import Path

import pytest
import pyperclip

from apps.yaal_examples_search.clipboard import Clipboard
from apps.yaal_examples_search.formatter.rich_tree_formatter import FormatResults, FormatResultId


def test_copy_path_to_clipboard_copies_selected_path_and_exits(monkeypatch, capsys):
    p = Path("/tmp/yaal_examples_search_test_path.txt")
    format_results = FormatResults(
        results={FormatResultId(7): p},
        content="does-not-matter",
    )

    # Select id=7, then press Enter to exit loop
    inputs = iter(["7", ""])
    monkeypatch.setattr("builtins.input", lambda _prompt: next(inputs))

    with __preserve_real_clipboard():
        Clipboard.copy_path_to_clipboard(format_results)

        got = pyperclip.paste()
        assert got == str(p.absolute())

    out = capsys.readouterr().out
    assert "Copying path:" in out
    assert str(p.absolute()) in out


def test_copy_path_to_clipboard_invalid_id_does_not_change_clipboard(monkeypatch, capsys):
    p = Path("/tmp/yaal_examples_search_test_path.txt")
    format_results = FormatResults(
        results={FormatResultId(1): p},
        content="does-not-matter",
    )

    # Enter an ID that is not in results, then exit
    inputs = iter(["999", ""])
    monkeypatch.setattr("builtins.input", lambda _prompt: next(inputs))

    with __preserve_real_clipboard() as before:
        Clipboard.copy_path_to_clipboard(format_results)
        after = pyperclip.paste()
        assert after == before

    out = capsys.readouterr().out
    assert "Invalid ID selected." in out


def test_copy_path_to_clipboard_empty_results_returns_without_touching_clipboard(monkeypatch):
    format_results = FormatResults(results={}, content="does-not-matter")

    # If input() gets called, that's a bug—make it obvious.
    def _boom(_prompt: str) -> str:
        raise AssertionError("input() should not be called when results are empty")

    monkeypatch.setattr("builtins.input", _boom)

    with __preserve_real_clipboard() as before:
        Clipboard.copy_path_to_clipboard(format_results)
        after = pyperclip.paste()
        assert after == before


@contextlib.contextmanager
def __preserve_real_clipboard():
    """
    Uses the real system clipboard via pyperclip.
    Skips if no clipboard mechanism is available on this machine/session.
    """
    try:
        before = pyperclip.paste()
    except pyperclip.PyperclipException as e:
        pytest.skip(f"Real clipboard not available: {e!r}")

    try:
        yield before
    finally:
        # Best-effort restore (even if test fails mid-way)
        try:
            pyperclip.copy(before)
        except pyperclip.PyperclipException:
            pass
