from pathlib import Path
from textwrap import dedent
from typing import Generator

import srt


def test_read_srt_file():
    file: Path = Path(__file__).parent / 'subtitles.srt'
    data: str = file.read_text()
    subs: Generator[srt.Subtitle] = srt.parse(data)
    for sub in subs:
        print(sub)


def test_read_srt_string():
    data: str = dedent("""
        1
        00:06:17,436 --> 00:06:19,104
        Pio! Pio! Pio!
        
        2
        00:07:27,194 --> 00:07:28,611
        <i>Ciao</i> Rome!
    """)
    subs: Generator[srt.Subtitle] = srt.parse(data)
    for sub in subs:
        print(sub)
