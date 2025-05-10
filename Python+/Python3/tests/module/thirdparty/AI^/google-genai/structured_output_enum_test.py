from enum import Enum

from google.genai import Client
from google.genai.types import GenerateContentResponse


class Instrument(Enum):
    PERCUSSION = "Percussion"
    STRING = "String"
    WOODWIND = "Woodwind"
    BRASS = "Brass"
    KEYBOARD = "Keyboard"


def test_structured_output(client: Client):
    response: GenerateContentResponse = client.models.generate_content(
        model='gemini-2.0-flash',
        contents='What type of instrument is an oboe?',
        config={
            'response_mime_type': 'text/x.enum',
            'response_schema': Instrument,
        },
    )
    print(response.text)
