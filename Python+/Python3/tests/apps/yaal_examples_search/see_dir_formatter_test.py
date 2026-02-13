import tempfile
from pathlib import Path
from textwrap import dedent

from apps.yaal_examples_search.see_dir_formatter import SeeDirFormatter


def test_format():
    base_dir: Path = Path(tempfile.mkdtemp())
    net_dir: Path = base_dir / "net"
    net_dir.mkdir()
    core_file: Path = net_dir / "core.fodg"
    streaming_file: Path = net_dir / "streaming.fodg"
    kafka_file: Path = net_dir / "kafka.fodg"
    core_file.touch()
    streaming_file.touch()
    kafka_file.touch()
    paths: list[Path] = [core_file, streaming_file]
    formatter: SeeDirFormatter = SeeDirFormatter(base_dir, paths)
    out: str = formatter.format()
    assert out == dedent(f"""\
                    {base_dir.name}/
                    └─net/
                      ├─core.fodg
                      └─streaming.fodg""")
