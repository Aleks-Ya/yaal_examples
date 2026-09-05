import sys
from pathlib import Path

import pytest

from yaal_helpers.temp_helper import TempPath


def pytest_collectstart(collector: pytest.Collector) -> None:
    """Let a test module import helper modules sitting next to it.

    Several examples import a plain sibling file rather than a package -- e.g.
    ``PyTest+/pytest-qt/hello_app_test.py`` does ``from hello_app import ...``. Those imports
    cannot be written as absolute ones, because directory names like ``PyTest+`` and ``AI+``
    contain ``+`` and are not valid Python identifiers.

    The default "prepend" import mode made these work by putting each test module's own
    directory on ``sys.path``. ``pytest.ini`` switches to ``--import-mode=importlib`` (which
    is what lets sibling directories reuse a filename, see the comment there), and importlib
    mode deliberately touches ``sys.path`` not at all -- so we restore just that one behaviour.
    """
    if isinstance(collector, pytest.Module):
        directory = str(collector.path.parent)
        if directory not in sys.path:
            sys.path.insert(0, directory)


@pytest.fixture
def temp_path_absent() -> Path:
    return TempPath.temp_path_absent()
