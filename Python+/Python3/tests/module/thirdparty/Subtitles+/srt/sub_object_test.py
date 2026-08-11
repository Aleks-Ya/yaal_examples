from datetime import timedelta
from textwrap import dedent
from typing import Generator

import pysrt
import srt
from pysrt import SubRipFile, SubRipTime


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
        index: int = sub.index
        start: timedelta = sub.start
        end: timedelta = sub.end
        content: str = sub.content
        proprietary: str = sub.proprietary
        print()
        print(f"Index: {index}")
        print(f"Start: {start}")
        print(f"End: {end}")
        print(f"Content: {content}")
        print(f"Proprietary: {proprietary}")

