from pathlib import Path

from openai import OpenAI
from openai.types.responses import Response


def test_format_file(client: OpenAI):
    content_file: Path = Path(__file__).parent / 'info.txt'
    content: str = content_file.read_text()
    response: Response = client.responses.create(
        model="gpt-4.1",
        instructions="You should return the same text, but each sentence should be in an individual line. If sentence has line breaks, remove them.",
        input=content
    )
    print(response.output_text)
