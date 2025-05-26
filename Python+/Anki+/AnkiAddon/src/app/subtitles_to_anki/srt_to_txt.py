from pathlib import Path

import pysrt
from openai import OpenAI
from openai.types.responses import Response
from pysrt import SubRipFile


class SrtToTxt:

    @staticmethod
    def srt_to_text(srt: Path, txt: Path) -> None:
        print(f"Converting SRT to TXT: {srt}")
        key: str = Path.home().joinpath(".openai").joinpath("token.txt").read_text()
        client: OpenAI = OpenAI(api_key=key)
        content: str = SrtToTxt.__parse_srt(srt)
        formatted: str = SrtToTxt.__remove_line_breaks(client, content)
        trimmed: str = SrtToTxt.__trim_lines(formatted)
        txt.write_text(trimmed)

    @staticmethod
    def __parse_srt(srt: Path) -> str:
        subs: SubRipFile = pysrt.open(srt)
        sentences: list[str] = []
        for sub in subs:
            text_without_tags: str = sub.text_without_tags
            sentence: str = text_without_tags.replace('\n', ' ')
            sentences.append(sentence)
        return '\n'.join(sentences)

    @staticmethod
    def __remove_line_breaks(client: OpenAI, content: str) -> str:
        response: Response = client.responses.create(
            model="gpt-4.1",
            instructions="You should return the same text, but each sentence should be in an individual line. If sentence has line breaks, remove the breaks.",
            input=content
        )
        return response.output_text

    @staticmethod
    def __trim_lines(text: str) -> str:
        lines: list[str] = [line.strip() for line in text.split('\n')]
        return '\n'.join(lines)
