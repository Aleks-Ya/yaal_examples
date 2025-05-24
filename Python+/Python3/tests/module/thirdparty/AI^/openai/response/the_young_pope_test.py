from pathlib import Path

import pysrt
from openai import OpenAI
from openai.types.responses import Response
from pysrt import SubRipFile


def test_srt_to_sentences(client: OpenAI):
    srt: Path = Path.home() / "Downloads/The.Young.Pope.S01E01.English.Full.srt"
    content: str = __parse_srt(srt)
    formatted: str = __remove_line_breaks(client, content)
    txt: Path = Path.home() / "Downloads/The.Young.Pope.S01E01.English.Full.txt"
    txt.write_text(formatted)


def __parse_srt(srt: Path) -> str:
    subs: SubRipFile = pysrt.open(srt)
    sentences: list[str] = []
    for sub in subs:
        text_without_tags: str = sub.text_without_tags
        sentence: str = text_without_tags.replace('\n', ' ')
        sentences.append(sentence)
    return '\n'.join(sentences)


def __remove_line_breaks(client: OpenAI, content: str) -> str:
    response: Response = client.responses.create(
        model="gpt-4.1",
        instructions="You should return the same text, but each sentence should be in an individual line. If sentence has line breaks, remove the breaks.",
        input=content
    )
    return response.output_text
