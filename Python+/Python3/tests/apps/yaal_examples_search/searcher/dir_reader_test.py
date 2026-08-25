from pathlib import Path

from apps.yaal_examples_search.searcher.dir_reader import DirReader


def test_finds_files_recursively(tmp_path: Path) -> None:
    (tmp_path / "top.md").touch()
    nested: Path = tmp_path / "Java+" / "JSE+"
    nested.mkdir(parents=True)
    (nested / "Core.java").touch()

    found: list[Path] = DirReader(tmp_path).get_all_files()

    assert sorted(found) == [nested / "Core.java", tmp_path / "top.md"]


def test_excluded_dirs_are_not_descended_into(tmp_path: Path) -> None:
    (tmp_path / "keep.py").touch()
    for excluded in ("__pycache__", ".git", "build", "node_modules"):
        excluded_dir: Path = tmp_path / excluded / "nested"
        excluded_dir.mkdir(parents=True)
        (excluded_dir / "ignored.txt").touch()

    found: list[Path] = DirReader(tmp_path).get_all_files()

    assert found == [tmp_path / "keep.py"]


def test_excluded_dir_nested_deeply_is_skipped(tmp_path: Path) -> None:
    project: Path = tmp_path / "Python+" / "Python3"
    (project / "src").mkdir(parents=True)
    (project / "src" / "app.py").touch()
    (project / ".venv" / "lib").mkdir(parents=True)
    (project / ".venv" / "lib" / "vendored.py").touch()

    found: list[Path] = DirReader(tmp_path).get_all_files()

    assert found == [project / "src" / "app.py"]


def test_empty_dir_returns_no_files(tmp_path: Path) -> None:
    assert DirReader(tmp_path).get_all_files() == []
