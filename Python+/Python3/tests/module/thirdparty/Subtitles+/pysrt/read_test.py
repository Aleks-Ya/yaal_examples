from pathlib import Path
from textwrap import dedent

import pysrt
from pysrt import SubRipFile


def test_read_srt_file():
    file: Path = Path(__file__).parent / 'subtitles.srt'
    subs: SubRipFile = pysrt.open(file)
    assert len(subs) == 6
    for sub in subs:
        print(sub)


def test_read_srt_string():
    srt: str = dedent("""
        1
        00:06:17,436 --> 00:06:19,104
        Pio! Pio! Pio!
        
        2
        00:07:27,194 --> 00:07:28,611
        <i>Ciao</i> Rome!
    """)
    subs: SubRipFile = pysrt.from_string(srt)
    assert len(subs) == 2
    for sub in subs:
        print(sub)
