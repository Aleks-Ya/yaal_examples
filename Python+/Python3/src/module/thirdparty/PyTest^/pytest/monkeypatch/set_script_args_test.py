import sys

from _pytest.monkeypatch import MonkeyPatch


def test_set_script_args(monkeypatch: MonkeyPatch):
    monkeypatch.setattr(sys, 'argv', ['myscript.py', 'arg1', 'arg2'])
    assert sys.argv[1] == 'arg1'
