from pathlib import Path

from app.subtitles_to_anki.srt_to_txt import SrtToTxt


def test_srt_to_text():
    srt: Path = Path.home() / "Downloads/The.Young.Pope.S01E01.English.Full.srt"
    txt: Path = Path.home() / "Downloads/The.Young.Pope.S01E01.English.Full.txt"
    SrtToTxt.srt_to_text(srt, txt)
