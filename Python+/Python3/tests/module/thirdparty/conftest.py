"""Keep dependency-isolated example directories out of the shared pytest run.

Some directories under ``tests/module/thirdparty`` carry their own uv project because their
packages are heavy or conflict with the shared ``requirements.txt`` -- see ``CLAUDE.md``. Their
tests and ``conftest.py`` files import those packages at module import time, so collecting them
from the shared environment is a collection *error*, not a skip.

A directory is isolated when its ``pyproject.toml`` declares a ``[tool.uv]`` table. It is
collected only while pytest runs under that directory's own ``.venv``, i.e. via::

    uv run --project tests/module/thirdparty/<Topic> pytest tests/module/thirdparty/<Topic>

``[tool.uv]`` rather than the mere presence of ``pyproject.toml`` is deliberate: the ``setuptools``,
``build_frontend`` and ``cython`` examples ship ``pyproject.toml``/``setup.py`` files as *subject
matter* (packaging fixtures to build), and none of those is a uv project. Keying on the declaration
rather than on ``uv.lock`` also means this works in a fresh clone, where the lock files -- which are
generated, and deliberately not committed for these example projects -- do not exist yet.
"""

import sys
import tomllib
from pathlib import Path

_HERE = Path(__file__).parent
_ACTIVE_VENV = Path(sys.prefix).resolve()


def _is_uv_project(pyproject: Path) -> bool:
    try:
        return "uv" in tomllib.loads(pyproject.read_text(encoding="utf-8")).get("tool", {})
    except (OSError, tomllib.TOMLDecodeError):
        return False


def _is_isolated(directory: Path) -> bool:
    resolved = directory.resolve()
    # Collect the directory when running under its own venv, and also when running under a nested
    # project's venv -- pytest must still descend through AI+ to reach AI+/NLP+.
    running_here = _ACTIVE_VENV == (resolved / ".venv") or _ACTIVE_VENV.is_relative_to(resolved)
    return not running_here


collect_ignore = [
    str(pyproject.parent.relative_to(_HERE))
    for pyproject in sorted(_HERE.rglob("pyproject.toml"))
    if ".venv" not in pyproject.parts and _is_uv_project(pyproject) and _is_isolated(pyproject.parent)
]
