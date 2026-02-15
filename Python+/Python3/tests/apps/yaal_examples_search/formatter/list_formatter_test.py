from pathlib import Path
from textwrap import dedent

from apps.yaal_examples_search.data_types import Keyword
from apps.yaal_examples_search.formatter.list_formatter import ListFormatter


def test_format():
    base_dir: Path = Path("/tmp/examples")
    paths: list[Path] = [
        base_dir / "nested/buildings.fodg",
        base_dir / "nested/streaming.fodg"
    ]
    formatter: ListFormatter = ListFormatter(base_dir, paths)
    keyword: Keyword = Keyword("streaming")
    out: str = formatter.format(keyword)
    assert out == dedent("""\
                    nested/buildings.fodg
                    nested/\x1b[31mstreaming\x1b[0m.fodg""")
