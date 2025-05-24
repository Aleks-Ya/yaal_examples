from pathlib import Path

from openai import OpenAI
from openai.types import FileObject
from openai.types.responses import Response


def test_file_upload(client: OpenAI):
    data: Path = Path(__file__).parent / 'data.pdf'
    uploaded_file: FileObject = client.files.create(
        file=data,
        purpose="user_data",
    )
    print(uploaded_file)

    response: Response = client.responses.create(
        model="gpt-4.1",
        input=[
            {
                "role": "user",
                "content": [
                    {
                        "type": "input_file",
                        "file_id": uploaded_file.id,
                    },
                    {
                        "type": "input_text",
                        "text": "What's the user name?",
                    },
                ]
            }
        ]
    )
    print(response.output_text)
