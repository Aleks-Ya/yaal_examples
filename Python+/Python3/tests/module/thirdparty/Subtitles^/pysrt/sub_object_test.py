from textwrap import dedent

import pysrt
from pysrt import SubRipFile, SubRipTime


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
        index: int = sub.index
        start: SubRipTime = sub.start
        end: SubRipTime = sub.end
        position: str = sub.position
        text: str = sub.text
        text_without_tags: str = sub.text_without_tags
        print()
        print(f"Index: {index}")
        print(f"Start: {start}")
        print(f"End: {end}")
        print(f"Position: {position}")
        print(f"Text: {text}")
        print(f"Text without tags: {text_without_tags}")
